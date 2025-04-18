package com.nikhil.springboot.AtithiStay.dto;

import com.nikhil.springboot.AtithiStay.entity.enums.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String password;
    private Role role;
}
