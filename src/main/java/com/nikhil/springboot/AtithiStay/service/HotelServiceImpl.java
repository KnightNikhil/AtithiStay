package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.HotelDto;
import com.nikhil.springboot.AtithiStay.entity.Hotel;
import com.nikhil.springboot.AtithiStay.entity.Room;
import com.nikhil.springboot.AtithiStay.exceptions.ResourceNotFoundException;
import com.nikhil.springboot.AtithiStay.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService{

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoomService roomService;

    @Autowired
    InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id)) ;
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        Hotel newHotel = modelMapper.map(hotelDto, Hotel.class);
        newHotel.setId(id);
        newHotel.setActive(false);
        hotelRepository.save(newHotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public Boolean deleteHotelById(Long id) {
        Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        for(Room room :hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomService.deleteRoomById(room.getId());
        }
        hotelRepository.deleteById(id);


        return true;
    }

    @Override
    @Transactional
    public HotelDto activateHotelById(Long id) {
        Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        hotel.setActive(true);

//        for(Room room : hotel.getRooms()){
//            inventoryService.initilaizeRoomForAWeek(room);
//        }

        Hotel activatedHotel = hotelRepository.save(hotel);
        return modelMapper.map(activatedHotel, HotelDto.class);
    }

    @Override
    public HotelDto deActivateHotelById(Long id) {
        Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        hotel.setActive(false);
        Hotel activatedHotel = hotelRepository.save(hotel);
        return modelMapper.map(activatedHotel, HotelDto.class);
    }
}
