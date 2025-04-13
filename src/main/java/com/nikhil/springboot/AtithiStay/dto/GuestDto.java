package com.nikhil.springboot.AtithiStay.dto;

import com.nikhil.springboot.AtithiStay.entity.User;
import com.nikhil.springboot.AtithiStay.entity.enums.Gender;

public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
