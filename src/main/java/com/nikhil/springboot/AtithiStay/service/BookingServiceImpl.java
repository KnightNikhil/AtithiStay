package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.BookingDto;
import com.nikhil.springboot.AtithiStay.dto.BookingRequest;
import com.nikhil.springboot.AtithiStay.entity.*;
import com.nikhil.springboot.AtithiStay.entity.enums.BookingStatus;
import com.nikhil.springboot.AtithiStay.exceptions.ResourceNotFoundException;
import com.nikhil.springboot.AtithiStay.repository.*;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
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

    @Autowired
    CheckoutService checkoutService;


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
//                    inventory.setBookedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
                    inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
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
                .bookingStatus(BookingStatus.RESERVED)
                .build();

        bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public String initialisePayment(Long bookingId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ResourceNotFoundException("Booking not found with id: "+bookingId));

        String checkoutSessionUrl = checkoutService.getCheckoutSession(user, booking, "http://localhost:8080/success", "http://localhost:8080/failure");

        return  checkoutSessionUrl;
    }

    @Override
    @Transactional
    public void capturePayment(Event event) {
        if ("checkout.session.completed".equals(event.getType())) {
            EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

            Session session;

            if (deserializer.getObject().isPresent()) {
                session = (Session) deserializer.getObject().get(); // deserializer not working
            } else {
                session = ApiResource.GSON.fromJson(event.getData().getObject().toJson(), Session.class);
                // manually deserializing
            }

            if (session == null) return;

            String sessionId = session.getId();
            Booking booking =
                    bookingRepository.findByPaymentSessionId(sessionId).orElseThrow(() ->
                            new ResourceNotFoundException("Booking not found for session ID: "+sessionId));

            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);

            inventoryRepository.findAndLockReservedInventory(booking.getRoom().getId(), booking.getCheckInDate(),
                    booking.getCheckOutDate(), booking.getRoomsCount());

            inventoryRepository.confirmBooking(booking.getRoom().getId(), booking.getCheckInDate(),
                    booking.getCheckOutDate(), booking.getRoomsCount());

        } else {
            log.warn("Unhandled event type: {}", event.getType());
        }
    }

    private BookingDto cancelBooking(BookingRequest bookingRequest){
        BookingDto bookingDto = modelMapper.map(bookingRequest, BookingDto.class);
        bookingDto.setBookingStatus(BookingStatus.CANCELLED);
        return bookingDto;
    }



}
