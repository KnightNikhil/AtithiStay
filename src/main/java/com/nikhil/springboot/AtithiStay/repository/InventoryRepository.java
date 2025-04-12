package com.nikhil.springboot.AtithiStay.repository;

import com.nikhil.springboot.AtithiStay.entity.Inventory;
import com.nikhil.springboot.AtithiStay.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteAllByRoom(Room room);
}
