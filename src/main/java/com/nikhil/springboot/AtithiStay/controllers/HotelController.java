package com.nikhil.springboot.AtithiStay.controllers;

import com.nikhil.springboot.AtithiStay.dto.HotelDto;
import com.nikhil.springboot.AtithiStay.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping(path = "/createHotel")
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto){
        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping(path = "/getHotelById/{id}")
    public ResponseEntity<HotelDto> getHotel(@PathVariable Long id){
        HotelDto hotel = hotelService.getHotelById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @PostMapping(path = "/updateHotelById/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto){
        HotelDto hotel = hotelService.updateHotelById(id, hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteHotelById/{id}")
    public ResponseEntity<Boolean> deleteHotel(@PathVariable Long id){
        Boolean result = hotelService.deleteHotelById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/activateHotelById/{id}")
    public ResponseEntity<HotelDto> activateHotelById(@PathVariable Long id){
        HotelDto hotel = hotelService.activateHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @GetMapping(path = "/deActivateHotelById/{id}")
    public ResponseEntity<HotelDto> deActivateHotelById(@PathVariable Long id){
        HotelDto hotel = hotelService.deActivateHotelById(id);
        return ResponseEntity.ok(hotel);
    }

}
