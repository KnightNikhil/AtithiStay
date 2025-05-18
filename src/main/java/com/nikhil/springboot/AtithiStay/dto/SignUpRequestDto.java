package com.nikhil.springboot.AtithiStay.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String email;
    private String name;
    private String password;
}
