package com.nikhil.springboot.AtithiStay.dto;

import com.nikhil.springboot.AtithiStay.entity.HotelContactInfo;
import lombok.Data;

@Data
public class HotelDto {
    private Long id;

    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private Boolean active;

    private HotelContactInfo contactInfo;
}
