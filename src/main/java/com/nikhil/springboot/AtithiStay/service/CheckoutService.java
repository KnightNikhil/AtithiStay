package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.entity.Booking;
import com.nikhil.springboot.AtithiStay.entity.User;

public interface CheckoutService {

    String getCheckoutSession(User user, Booking booking, String successUrl, String failureUrl);
}
