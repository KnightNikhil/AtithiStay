package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.RoomDto;

import java.util.List;

public interface RoomService {


    RoomDto createNewRoom(RoomDto roomDto, Long hotelId);

    RoomDto getRoomById(Long id);

    Boolean deleteRoomById(Long id );

    List<RoomDto> getAllRoomsInHotel(Long hotelId);
}
