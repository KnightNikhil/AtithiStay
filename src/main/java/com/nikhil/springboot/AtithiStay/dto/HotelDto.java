package com.nikhil.springboot.AtithiStay.dto;

import com.nikhil.springboot.AtithiStay.entity.HotelContactInfo;
import com.nikhil.springboot.AtithiStay.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {
    private Long id;

    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private Boolean active;

    private HotelContactInfo contactInfo;

    private List<RoomDto> rooms;

}
