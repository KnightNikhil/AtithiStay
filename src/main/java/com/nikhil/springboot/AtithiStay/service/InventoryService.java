package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.HotelDto;
import com.nikhil.springboot.AtithiStay.dto.HotelSearchRequest;
import com.nikhil.springboot.AtithiStay.entity.Room;

import java.util.List;

public interface InventoryService {

    public Boolean deleteAllInventories(Room room);

    public void initilaizeRoomForAWeek(Room room);

    List<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
