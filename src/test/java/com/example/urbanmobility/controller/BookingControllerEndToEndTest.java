package com.example.urbanmobility.controller;

import com.example.urbanmobility.model.Booking;
import com.example.urbanmobility.service.BookingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingControllerEndToEndTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;


    private Booking validBooking;

    @BeforeEach
    public void setup() {
        validBooking = new Booking();
        validBooking.setUsername("testUser");
        validBooking.setRouteId(1);
    }

    //METHOD: createBooking
    @Test
    public void createBooking_Should_ReturnCreated_OnSuccess() throws Exception {
        // Converting the validBooking object to JSON
        String bookingJson = objectMapper.writeValueAsString(validBooking);

        // Performing an HTTP POST request to create a booking
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.routeId").value(1));
    }
    @Test
    public void createBooking_Should_ReturnBadRequest_OnNullBooking() throws Exception {
        // Performing an HTTP POST request with an empty body
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void createBooking_Should_ReturnBadRequest_OnEmptyUsername() throws Exception {
        // Converting the booking object with an empty username to JSON
        Booking bookingWithEmptyUsername = new Booking();
        bookingWithEmptyUsername.setUsername("");
        bookingWithEmptyUsername.setRouteId(1);
        String bookingJson = objectMapper.writeValueAsString(bookingWithEmptyUsername);

        // Performing an HTTP POST request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void createBooking_Should_ReturnBadRequest_OnInvalidRouteId() throws Exception {
        // Converting the booking object with an invalid routeId to JSON
        Booking bookingWithInvalidRouteId = new Booking();
        bookingWithInvalidRouteId.setUsername("testUser");
        bookingWithInvalidRouteId.setRouteId(-1);
        String bookingJson = objectMapper.writeValueAsString(bookingWithInvalidRouteId);

        // Performing an HTTP POST request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isBadRequest());
    }

    //METHOD: deleteBooking
    @Test
    public void deleteBooking_Should_ReturnNoContent_AfterDeletingBooking() throws Exception {
        // Creating a new booking
        String bookingJson = objectMapper.writeValueAsString(validBooking);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Getting the bookingId from the response
        String responseJson = result.getResponse().getContentAsString();
        JsonNode responseNode = objectMapper.readTree(responseJson);
        Long existingBookingId = responseNode.get("bookingId").asLong();

        // Deleting the booking with the extracted ID
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/bookings/" + existingBookingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deleteBooking_Should_ReturnNotFound_OnNonExistingBooking() throws Exception {
        Long nonExistingBookingId = 99999L; // Assuming this ID does not correspond to any booking
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/bookings/" + nonExistingBookingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void deleteBooking_Should_ReturnBadRequest_OnInvalidBookingId() throws Exception {
        String invalidBookingId = "invalidId"; //A non-numeric string
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/bookings/" + invalidBookingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBooking_Should_ReturnNotFound_OnBookingWithoutId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/bookings/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void getBookingById() {
    }

    @Test
    void getAllBookings() {
    }

    @Test
    void updateBooking() {
    }


}