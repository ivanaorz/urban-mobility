package com.example.urbanmobility.controller;

import com.example.urbanmobility.model.Account;
import com.example.urbanmobility.service.AccountService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerEndToEndTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper to convert Java objects to JSON

    @Autowired
    private AccountService accountService;

    private Account account;


    @BeforeEach
    public void setUp() {
        // Creating an Account object
        account = Account.builder()
                .id(1L)
                .username("Tom")
                .role("User")
                .paymentInfo("3334 5566 3432 9090")
                .paymentHistory(4)
                .isPaymentSet(true)
                .phone("0722946563")
                .activeBookings("3")
                .build();
    }

    //METHOD: createAccount
    @Test
    void createAccount_Should_ReturnCreated_OnSuccess() throws Exception {
        // Converting the Account object to JSON
        String accountJson = objectMapper.writeValueAsString(account);

        // Performing an HTTP POST request to create an account
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(status().isCreated());
    }

    @Test
    void createAccount_Should_ReturnBadRequest_OnValidationFailure() throws Exception {
        // Creating an invalid Account object
        Account invalidAccount = Account.builder()
                .id(1L)
                .username(" ") //Missing username data
                .role("User")
                .paymentInfo("3334 5566 3432 9090")
                .paymentHistory(4)
                .isPaymentSet(true)
                .phone("0722946563")
                .activeBookings("3")
                .build();

        // Converting the invalid Account object to JSON
        String invalidAccountJson = objectMapper.writeValueAsString(invalidAccount);

        // Performing an HTTP POST request to create an account
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAccountJson))
                .andExpect(status().isBadRequest());
    }

    //METHOD: deleteAccount

    @Test
    void deleteAccount_Should_ReturnHttpStatusOk() throws Exception {
        // Saving the account in the database
        accountService.createAccount(account);

        // Deleting the existing account
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/account/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Account was deleted successfully"));
    }



    //METHOD: updateAccount

    @Test
    void updateAccount_Should_ReturnUpdatedAccount_OnSuccess() throws Exception {
        accountService.createAccount(account);
        // Creating a new Account object with updated information
        Account updatedAccount = Account.builder()
                .id(1L)
                .username("UpdatedUser")
                .role("Admin")
                .paymentInfo("1234 5678 9012 3456")
                .paymentHistory(5)
                .isPaymentSet(true)
                .phone("9876543210")
                .activeBookings("5")
                .build();

        String updatedAccountJson = objectMapper.writeValueAsString(updatedAccount);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/account/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAccountJson))
                .andExpect(status().isOk())
                .andReturn();

        // Converting the response content to a JSON object
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper responseMapper = new ObjectMapper();
        JsonNode jsonResponse = responseMapper.readTree(responseContent);

        assertEquals("UpdatedUser", jsonResponse.get("username").asText());
        assertEquals("Admin", jsonResponse.get("role").asText());

    }
    @Test
    void getAccountById_Should_ReturnAccount_WhenValidId() throws Exception {
        // Create a sample Account
        Account sampleAccount = Account.builder()
                .id(1L)
                .username("Tom")
                .role("User")
                .paymentInfo("3334 5566 3432 9090")
                .paymentHistory(4)
                .isPaymentSet(true)
                .phone("0722946563")
                .activeBookings("3")
                .build();

        // Save the sample Account to the database or repository
        accountService.createAccount(sampleAccount);

        // Perform an HTTP GET request to retrieve the account by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)) // Verify the ID
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Tom")) // Verify other fields as needed
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("User"));
        // Add more assertions to verify other fields as needed
    }







}


