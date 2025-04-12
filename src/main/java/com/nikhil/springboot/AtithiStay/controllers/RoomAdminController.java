package com.nikhil.springboot.AtithiStay.controllers;


import com.nikhil.springboot.AtithiStay.dto.HotelDto;
import com.nikhil.springboot.AtithiStay.dto.RoomDto;
import com.nikhil.springboot.AtithiStay.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/hotels/{hotelId}/room")
public class RoomAdminController {

    @Autowired
    RoomService roomService;


    @PostMapping(path = "/createRoom")
    public ResponseEntity<RoomDto> createHotel(@RequestBody RoomDto roomDto, @PathVariable Long hotelId){
        RoomDto room = roomService.createNewRoom(roomDto, hotelId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping(path = "/getRoomById/{id}")
    public ResponseEntity<RoomDto> getHotel(@PathVariable Long id){
        RoomDto room = roomService.getRoomById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }


    @DeleteMapping(path = "/deleteRoomById/{id}")
    public ResponseEntity<Boolean> deleteHotel(@PathVariable Long id ){
        Boolean result = roomService.deleteRoomById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/getAllRoomsInHotel")
    public ResponseEntity<List<RoomDto>> activateHotelById(@PathVariable Long hotelId){
        List<RoomDto> rooms = roomService.getAllRoomsInHotel(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }




}
