package com.nikhil.springboot.AtithiStay.repository;

import com.nikhil.springboot.AtithiStay.entity.Hotel;
import com.nikhil.springboot.AtithiStay.entity.Inventory;
import com.nikhil.springboot.AtithiStay.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteAllByRoom(Room room);

    @Query("""
            SELECT DISTINCT i.hotel
            FROM Inventory i
            WHERE i.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.closed = false
                AND (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
           GROUP BY i.hotel, i.room
           HAVING COUNT(i.date) = :dateCount
           """)
    List<Hotel> findHotelsWithAvailableInventory(@Param("city") String city,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("roomsCount") Integer roomsCount,
                                                 @Param("dateCount") Long dateCount); // TODO what is the significance of this?

    Inventory findByHotelIdAndRoomIdAndDate(Long hotelId, Long roomId, LocalDate date);

    List<Inventory> findByRoomIdAndDateBetween(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    List<Inventory> findAllByRoomIdAndDateBetweenAndClosed(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, boolean b);

//    void confirmBooking(Long id, LocalDate checkInDate, LocalDate checkOutDate, Integer roomsCount);
}
