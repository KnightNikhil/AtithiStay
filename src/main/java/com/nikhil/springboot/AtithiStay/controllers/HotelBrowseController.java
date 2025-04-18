package com.nikhil.springboot.AtithiStay.controllers;

import com.nikhil.springboot.AtithiStay.dto.HotelDto;
import com.nikhil.springboot.AtithiStay.dto.HotelInfoDto;
import com.nikhil.springboot.AtithiStay.dto.HotelSearchRequest;
import com.nikhil.springboot.AtithiStay.service.HotelService;
import com.nikhil.springboot.AtithiStay.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/hotels")
public class HotelBrowseController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    HotelService hotelService;

    // TODO add paginantion
    @PostMapping(path = "/searchHotels")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDto> hotelDtos = inventoryService.searchHotels(hotelSearchRequest);
        return new ResponseEntity<>(hotelDtos, HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelDto> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
