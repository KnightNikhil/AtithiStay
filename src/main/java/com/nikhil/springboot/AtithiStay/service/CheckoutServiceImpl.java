package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.entity.Booking;
import com.nikhil.springboot.AtithiStay.entity.User;
import com.nikhil.springboot.AtithiStay.entity.enums.BookingStatus;
import com.nikhil.springboot.AtithiStay.repository.BookingRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    BookingRepository bookingRepository;


    @Override
    public String getCheckoutSession(User user, Booking booking, String successUrl, String failureUrl) {

        try {

            CustomerCreateParams customerParam = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .setName(user.getName())
                    .build();

            Customer customer = Customer.create(customerParam);

            SessionCreateParams sessionParams = SessionCreateParams.builder()

                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCustomer(customer.getId())
                    .setCurrency("inr")
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(failureUrl)
                    .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("inr")
                                                    .setUnitAmount(booking.getRoom().getBasePrice().multiply(BigDecimal.valueOf(100L)).longValue())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(booking.getHotel().getName() + booking.getRoom().getType())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(sessionParams);
            booking.setPaymentSessionId(session.getId());
            booking.setBookingStatus(BookingStatus.PAYMENT_PENDING);
            bookingRepository.save(booking);

            return session.getUrl();

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }
}
