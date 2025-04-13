package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.HotelDto;
import com.nikhil.springboot.AtithiStay.dto.HotelSearchRequest;
import com.nikhil.springboot.AtithiStay.entity.Hotel;
import com.nikhil.springboot.AtithiStay.entity.Inventory;
import com.nikhil.springboot.AtithiStay.entity.Room;
import com.nikhil.springboot.AtithiStay.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Boolean deleteAllInventories(Room room){
        inventoryRepository.deleteAllByRoom(room);
        return true;
    }


    @Override
    public void initilaizeRoomForAWeek(Room room){
        LocalDate today =  LocalDate.now();
        LocalDate endDate = today.plusDays(7);
        for(; !today.isAfter(endDate) ; today = today.plusDays(1)  ) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .city(room.getHotel().getCity())
                    .price(room.getBasePrice())
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .bookedCount(0)
                    .surgeFactor(BigDecimal.ONE)
                    .date(today)
                    .room(room)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public List<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        long dateCount =
                ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;
        List<Hotel> hotels= inventoryRepository.findHotelsWithAvailableInventory(
                hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate(), hotelSearchRequest.getRoomsCount(), dateCount
        );
        List<HotelDto> hotelDtos = hotels.stream().map(hotel ->
            modelMapper.map(hotel, HotelDto.class))
                .toList();
        return hotelDtos;
    }


}
