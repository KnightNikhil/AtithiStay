package com.nikhil.springboot.AtithiStay.controllers;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;
import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.dto.GuestDto;
import com.nikhil.springboot.AtithiStay.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/booking")
public class HotelBookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/startBooking")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        BookingDto bookingDto = bookingService.initialiseBooking(bookingRequest);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @PostMapping("/addGuest")
    public ResponseEntity<BookingDto> addGuests(@RequestBody GuestDto guestDto){
        BookingDto bookingDto = bookingService.addGuests(guestDto);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }


}
