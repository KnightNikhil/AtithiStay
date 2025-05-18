package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.UserDto;
import com.nikhil.springboot.AtithiStay.entity.User;

public interface UserService {
    UserDto createNewUser(UserDto userDto);

    User getUserById(Long userId);
}
