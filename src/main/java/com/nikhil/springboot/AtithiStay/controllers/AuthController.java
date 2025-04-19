package com.nikhil.springboot.AtithiStay.controllers;

import com.nikhil.springboot.AtithiStay.advice.ApiResponse;
import com.nikhil.springboot.AtithiStay.dto.LoginRequestDto;
import com.nikhil.springboot.AtithiStay.dto.SignUpRequestDto;
import com.nikhil.springboot.AtithiStay.dto.UserDto;
import com.nikhil.springboot.AtithiStay.entity.enums.Role;
import com.nikhil.springboot.AtithiStay.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(path="/userSignUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(authService.signUp(signUpRequestDto, Set.of(Role.GUEST)), HttpStatus.CREATED);
    }

    @PostMapping(path="/userLogin")
    public ResponseEntity<ApiResponse<String>> logIn(@RequestBody LoginRequestDto loginRequestDto){
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(token));
    }

    @PostMapping(path="/adminSignUp")
    public ResponseEntity<UserDto> signUpAdmin(@RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(authService.signUp(signUpRequestDto, Set.of(Role.GUEST, Role.HOTEL_MANAGER)), HttpStatus.CREATED);
    }

}
