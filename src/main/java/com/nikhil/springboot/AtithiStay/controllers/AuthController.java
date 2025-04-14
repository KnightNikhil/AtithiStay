package com.nikhil.springboot.AtithiStay.controllers;


import com.nikhil.springboot.AtithiStay.dto.LoginRequestDto;
import com.nikhil.springboot.AtithiStay.dto.SignUpRequestDto;
import com.nikhil.springboot.AtithiStay.dto.UserDto;
import com.nikhil.springboot.AtithiStay.security.AuthService;
import com.nikhil.springboot.AtithiStay.security.JWTService;
import com.nikhil.springboot.AtithiStay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JWTService jwtService;

    @PostMapping(path="/userSignUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(authService.signUp(signUpRequestDto), HttpStatus.OK);
    }

    @PostMapping(path="/userLogin")
    public ResponseEntity<String> logIn(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }


}
