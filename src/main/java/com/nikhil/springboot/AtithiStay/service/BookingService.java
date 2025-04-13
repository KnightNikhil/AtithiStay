package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;

public interface BookingService {
    public BookingDto initialiseBooking();
    public BookingDto addGuests();
}
