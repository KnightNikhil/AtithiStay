package com.nikhil.springboot.AtithiStay.controllers;


import com.nikhil.springboot.AtithiStay.service.BookingService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebHookController {

    @Autowired
    BookingService bookingService;


    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/payment")
    @Operation(summary = "Capture the payments", tags = {"Webhook"})
    public ResponseEntity<String> capturePayments(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            bookingService.capturePayment(event);
            return new ResponseEntity<>("Webhook received ", HttpStatus.OK);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }
    }


}
