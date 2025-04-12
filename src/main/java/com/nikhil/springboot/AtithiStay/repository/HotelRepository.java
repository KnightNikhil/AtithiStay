package com.nikhil.springboot.AtithiStay.repository;

import com.nikhil.springboot.AtithiStay.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long > {

}
