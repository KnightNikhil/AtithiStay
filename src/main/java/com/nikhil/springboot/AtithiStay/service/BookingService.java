package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;
import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.dto.GuestDto;
import com.stripe.model.Event;

public interface BookingService {
    public BookingDto initialiseBooking(BookingRequest bookingRequest);

    String initialisePayment(Long bookingId);

    void capturePayment(Event event);
}
