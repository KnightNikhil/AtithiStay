package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.entity.Room;

public interface InventoryService {

    public Boolean deleteAllInventories(Room room);

    public void initilaizeRoomForAWeek(Room room);
}
