package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;
import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.entity.*;
import com.nikhil.springboot.AtithiStay.entity.enums.BookingStatus;
import com.nikhil.springboot.AtithiStay.exceptions.ResourceNotFoundException;
import com.nikhil.springboot.AtithiStay.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with Hotel id " + bookingRequest.getHotelId() +"not found" ));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room with Room id " + bookingRequest.getRoomId() +"not found" ));


        long dateCount =
                ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()) + 1;

        List<Inventory> inventories =  inventoryRepository.findAllByRoomIdAndDateBetweenAndClosed(bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), false);

        if(inventories.size() == dateCount){
            for(Inventory inventory : inventories){
                if( (inventory.getTotalCount() - inventory.getReservedCount() - inventory.getBookedCount() - bookingRequest.getRoomsCount())>=0){
                    inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomsCount());
                }
                else{
                    return cancelBooking(bookingRequest);
                }
            }
            inventoryRepository.saveAll(inventories);
        }
        else{
            return cancelBooking(bookingRequest);
        }


        Booking booking = Booking.builder()
                .room(room)
                .hotel(hotel)
                .user(user)
                .roomsCount(bookingRequest.getRoomsCount())
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        bookingRepository.save(booking);



        return modelMapper.map(booking, BookingDto.class);
    }

    private BookingDto cancelBooking(BookingRequest bookingRequest){
        BookingDto bookingDto = modelMapper.map(bookingRequest, BookingDto.class);
        bookingDto.setBookingStatus(BookingStatus.CANCELLED);
        return bookingDto;
    }



}
