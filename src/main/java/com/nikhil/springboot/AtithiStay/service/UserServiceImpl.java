package com.nikhil.springboot.AtithiStay.service;

import com.nikhil.springboot.AtithiStay.dto.UserDto;
import com.nikhil.springboot.AtithiStay.entity.User;
import com.nikhil.springboot.AtithiStay.entity.enums.Role;
import com.nikhil.springboot.AtithiStay.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createNewUser(UserDto userDto) throws IllegalArgumentException {
        if(userRepository.existsByEmail(userDto.getEmail())){
            new IllegalArgumentException("Email already exists");
        return null;
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(encryptPassword(userDto.getPassword()));
        user.setRoles(Set.of(Role.GUEST));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    private String encryptPassword(String password){
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        //TODO::
        /*
         loadUserByUsername method is called from UserDetailsService interface (through spring security dependency),
         we implemented the loadUserByUsername implemented in our the code in UserServiceImpl and return User
         loadUserByUsername should return UserDetails so we implements our User entity with it, or else we could have also
         created the spring security User bean using our app_user (User entity) using builder method (as done below),
         and now springSecurity will manage everything, if the passwords matches or not with the username provided by encoding itself.
         */


//        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .roles(user.getRoles().toArray(new String[0]))
//                .build();

        return user;
    }
}
