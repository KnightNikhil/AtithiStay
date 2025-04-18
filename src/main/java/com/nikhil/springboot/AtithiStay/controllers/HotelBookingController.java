package com.nikhil.springboot.AtithiStay.controllers;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;
import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/booking")
public class HotelBookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/startBooking")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        BookingDto bookingDto = bookingService.initialiseBooking(bookingRequest);
        return ResponseEntity.ok(bookingDto);
    }

    @GetMapping("/initPayment/{bookingId}")
    public ResponseEntity<String> initialiseBooking(@PathVariable Long bookingId){
        String paymentSessionId = bookingService.initialisePayment(bookingId);
        return ResponseEntity.ok(paymentSessionId);
    }

    @GetMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking Cancelled and amount Refunded");
    }

}
