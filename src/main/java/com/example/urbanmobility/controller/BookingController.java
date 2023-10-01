package com.example.urbanmobility.controller;

import com.example.urbanmobility.exception.ResourceNotFoundException;
import com.example.urbanmobility.model.Booking;
import com.example.urbanmobility.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.createBooking(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }
//@PostMapping
//public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
//    try {
//        Booking savedBooking = bookingService.createBooking(booking);
//        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
//    } catch (IllegalArgumentException e) {
//        // Return 400 Bad Request for null Booking object
//        return new ResponseEntity<>("Invalid Booking Data", HttpStatus.BAD_REQUEST);
//    } catch (DataIntegrityViolationException e) {
//        // Return 400 Bad Request for empty username or invalid routeId
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//}

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId)
                .map(booking -> new ResponseEntity<>(booking, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        try {
            Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        try {
            bookingService.deleteBooking(bookingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
