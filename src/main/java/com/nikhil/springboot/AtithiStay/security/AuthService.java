package com.nikhil.springboot.AtithiStay.security;

import com.nikhil.springboot.AtithiStay.dto.LoginRequestDto;
import com.nikhil.springboot.AtithiStay.dto.SignUpRequestDto;
import com.nikhil.springboot.AtithiStay.dto.UserDto;
import com.nikhil.springboot.AtithiStay.entity.User;
import com.nikhil.springboot.AtithiStay.entity.enums.Role;
import com.nikhil.springboot.AtithiStay.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;


    @Transactional
    public UserDto signUp(SignUpRequestDto signUpRequestDto, Set<Role> role) {

        if(userRepository.existsByEmail(signUpRequestDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        User user = modelMapper.map(signUpRequestDto, User.class);
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setRoles(role);
        return modelMapper.map(userRepository.save(user), UserDto.class);

    }


    public String login(LoginRequestDto loginRequestDto) {

        // TODO::
        /*

        UsernamePasswordAuthenticationToken - Wraps credentials for authentication

        authentication manager will check what kind of AuthenticationProvider to choose, it will loop over all the providers
         for db calls - DaoAuthenticationProvider is used
         then loadUserByUsername method is called from UserDetailsService interface (through spring security dependency),
         we implemented the loadUserByUsername implemented in our the code in UserServiceImpl and return User
         loadUserByUsername should return UserDetails so we implements our User entity with it, or else we could have also
         created the spring security User bean using our app_user (User entity) using builder method,
         and now springSecurity will go to configure method in Configuration file we created where we will tell which password encoder to use,
         this will check if the passwords matches or not with the provided password.

                It will:
        	•	Look up the user by email
        	•	Compare the password (after encoding)
         	•	Return an Authentication object if successful, or throw BadCredentialsException if invalid
         */

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), loginRequestDto.getPassword()
        ));

        // Once logged in, we can now use this user as it is stored in SecurityContextHolder
        //  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateJWTToken(user);

        return token;

    }


    }
