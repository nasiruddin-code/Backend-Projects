package com.codera.hotelbooking.controller;

import com.codera.hotelbooking.model.transactional.BookingDetails;
import com.codera.hotelbooking.service.BookingDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking-details")
@Tag(name="BookingDetailsController", description="BookingDetails Management")
public class BookingDetailsController {

    private final BookingDetailsService bookingDetailsService;

    @Autowired
    public BookingDetailsController(BookingDetailsService bookingDetailsService) {
        this.bookingDetailsService = bookingDetailsService;
    }

    @Operation(
    		summary="Get Operation",
    		description="Retrieves a list of all booking details"
    		)
    @GetMapping
    public ResponseEntity<List<BookingDetails>> getAllBookingDetails() {
        List<BookingDetails> bookingDetails = bookingDetailsService.getAllBookingDetails();
        return ResponseEntity.ok(bookingDetails);
    }

    @Operation(
    		summary="Get Operation by its ID",
    		description="Retrieves specific booking details by its ID"
    		)
    @GetMapping("/{id}")
    public ResponseEntity<BookingDetails> getBookingDetailsById(@PathVariable("id") Long id) {
        Optional<BookingDetails> bookingDetails = bookingDetailsService.getBookingDetailsById(id);
        return bookingDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @Operation(
    		summary="Post Operation",
    		description="Creates new booking details"
    		)
    @PostMapping
    public ResponseEntity<BookingDetails> createBookingDetails(@RequestBody BookingDetails bookingDetails) {
        BookingDetails createdBookingDetails = bookingDetailsService.createBookingDetails(bookingDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookingDetails);
    }
    
    @Operation(
    		summary="Put Operation by its ID",
    		description="Updates existing booking details with the specified ID"
    		)
    @PutMapping("/{id}")
    public ResponseEntity<BookingDetails> updateBookingDetails(@PathVariable("id") Long id, @RequestBody BookingDetails bookingDetails) {
        bookingDetails.setId(id); // Set the ID from the path variable
        BookingDetails updatedBookingDetails = bookingDetailsService.updateBookingDetails(bookingDetails);
        return ResponseEntity.ok(updatedBookingDetails);
    }

    @Operation(
    		summary="Delete Operation by its ID",
    		description="Deletes booking details with the specified ID"
    		)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingDetails(@PathVariable("id") Long id) {
        bookingDetailsService.deleteBookingDetails(id);
        return ResponseEntity.noContent().build();
    }
}
