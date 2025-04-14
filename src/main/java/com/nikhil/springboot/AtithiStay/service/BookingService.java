package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;
import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.dto.GuestDto;

public interface BookingService {
    public BookingDto initialiseBooking(BookingRequest bookingRequest);
}
