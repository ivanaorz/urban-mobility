package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Booking;
import com.example.urbanmobility.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceUnitTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;

    @BeforeEach
    public void setup() {
        booking = Booking.builder()
                .bookingId(1L)
                .routeId(101)
                .username("Valeria")
                .build();
    }

    //METHOD: createBooking
    @Test
    void createBooking_Should_ReturnSavedBooking_On_ValidBooking() {
        // Arrange
        when(bookingRepository.save(any())).thenReturn(booking);

        // Act
        Booking result = bookingService.createBooking(booking);

        // Assert
        assertNotNull(result);
        assertEquals(booking.getBookingId(), result.getBookingId());
    }
    @Test
    void createBooking_Should_ThrowException_On_NullBooking() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(null);
        });
    }

    @Test
    void createBooking_Should_ThrowException_On_BookingWithNullUsername() {
        // Arrange
        booking.setUsername(null);

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            bookingService.createBooking(booking);
        });
    }
    @Test
    void createBooking_Should_ThrowException_On_BookingWithInvalidRouteId() {
        // Arrange
        booking.setRouteId(-1);  // Assuming -1 is not a valid routeId

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    //METHOD: deleteBooking
    @Test
    void deleteBooking_Should_DeleteSuccessfully_AnExistingBooking() {
        long existingBookingId = 1L;
        Mockito.when(bookingRepository.existsById(existingBookingId)).thenReturn(true);

        assertDoesNotThrow(() -> bookingService.deleteBooking(existingBookingId));
        Mockito.verify(bookingRepository).deleteById(existingBookingId);
    }

    @Test
    void deleteBooking_Should_ThrowException_On_NonExistingBooking() {
        long nonExistingBookingId = 2L;
        Mockito.when(bookingRepository.existsById(nonExistingBookingId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.deleteBooking(nonExistingBookingId));
    }
    @Test
    void deleteBooking_Should_ThrowException_On_BookingWithHighestPossibleId() {
        long highestId = Long.MAX_VALUE;
        Mockito.when(bookingRepository.existsById(highestId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.deleteBooking(highestId));
    }

    @Test
    void deleteBooking_Should_ThrowException_On_BookingWithLowestPossibleId() {
        long lowestId = Long.MIN_VALUE;
        Mockito.when(bookingRepository.existsById(lowestId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.deleteBooking(lowestId));
    }

    //METHOD: updateBooking
    @Test
    void updateBooking_Should_ReturnUpdatedBooking_WithExistingBookingId() {

        Mockito.when(bookingRepository.existsById(booking.getBookingId())).thenReturn(true);
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);

        Booking updatedBooking = bookingService.updateBooking(booking.getBookingId(), booking);

        assertEquals(booking, updatedBooking);
    }

    @Test
    void updateBooking_Should_ThrowException_WithNonExistentBookingId() {
        Mockito.when(bookingRepository.existsById(booking.getBookingId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> bookingService.updateBooking(booking.getBookingId(), booking));
    }
    @Test
    void updateBooking_Should_ThrowException_WithNullBookingId() {
        Long nullBookingId = null;

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.updateBooking(nullBookingId, booking));
    }

    @Test
    void updateBooking_Should_StillUpdate_InconsistentBookingIdBetweenPathVariableAndRequestBody() {
        long pathVariableBookingId = 1L;
        Booking bookingWithDifferentId = Booking.builder()
                .bookingId(2L) // This ID is different from the path variable
                .routeId(103)
                .username("John")
                .build();

        Mockito.when(bookingRepository.existsById(pathVariableBookingId)).thenReturn(true);
        Mockito.when(bookingRepository.save(bookingWithDifferentId)).thenReturn(bookingWithDifferentId);

        Booking updatedBooking = bookingService.updateBooking(pathVariableBookingId, bookingWithDifferentId);

        assertEquals(bookingWithDifferentId, updatedBooking);
    }

    @Test
    void getBookingById() {
    }

    @Test
    void getAllBookings() {
    }



}