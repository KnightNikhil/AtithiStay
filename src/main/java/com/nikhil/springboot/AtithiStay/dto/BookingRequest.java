package com.nikhil.springboot.AtithiStay.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long id;
    private Long hotelId;
    private Long roomId;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer guests;
}
