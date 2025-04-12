package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.RoomDto;
import com.nikhil.springboot.AtithiStay.entity.Hotel;
import com.nikhil.springboot.AtithiStay.entity.Room;
import com.nikhil.springboot.AtithiStay.exceptions.ResourceNotFoundException;
import com.nikhil.springboot.AtithiStay.repository.HotelRepository;
import com.nikhil.springboot.AtithiStay.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public RoomDto createNewRoom(RoomDto roomDto, Long hotelId) {
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotelRepository.findById(hotelId).orElse(null));
        roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room= roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id)) ;
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public Boolean deleteRoomById(Long id) {
        Room room= roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id)) ;
        roomRepository.deleteById(id);
        return true;
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("No hotel found") );

        List<RoomDto> roomsDto = hotel.getRooms().stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .toList();

        return roomsDto;
    }


}
