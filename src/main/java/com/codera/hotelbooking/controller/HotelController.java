package com.codera.hotelbooking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codera.hotelbooking.model.master.Hotel;
import com.codera.hotelbooking.service.HotelService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
@Tag(name="HotelController", description="Hotel Management")
public class HotelController {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(
    		summary="Get Operation",
    		description="Retrieves a list of all hotels"
    		)
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @Operation(
    		summary="Get Operation by its ID",
    		description="Retrieves a specific hotel by its ID"
    		)
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = hotelService.getHotelById(id);
        return hotel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(
    		summary="Post Operation",
    		description="Creates a new hotel"
    		)
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }
    
    
    @Operation(
    		summary="Put Operation by its ID",
    		description="Updates an existing hotel with the specified ID"
    		)
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("id") Long id, @RequestBody Hotel hotel) {
        hotel.setId(id); // Set the ID from the path variable
        Hotel updatedHotel = hotelService.updateHotel(hotel);
        return ResponseEntity.ok(updatedHotel);
    }
    
    
    @Operation(
    		summary="Delete Operation by its ID",
    		description="Deletes a hotel with the specified ID"
    		)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}

