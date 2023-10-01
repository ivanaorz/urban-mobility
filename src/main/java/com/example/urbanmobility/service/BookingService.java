package com.example.urbanmobility.service;

import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Booking;
import com.example.urbanmobility.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking object cannot be null");
        }

        if (booking.getUsername() == null || booking.getUsername().trim().isEmpty()) {
            throw new DataIntegrityViolationException("Username is required for booking");
        }

        if (booking.getRouteId() <= 0) {  // Assuming route IDs start from 1
            throw new DataIntegrityViolationException("Invalid routeId for booking");
        }

        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public Booking updateBooking(Long bookingId, Booking booking) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }

        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking with id " + bookingId + " not found!");
        }

        booking.setBookingId(bookingId);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new ResourceNotFoundException("Booking with id " + bookingId + " not found!");
        }
    }
}
