
package com.nikhil.springboot.AtithiStay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //this tells the spring security that we are configuring it
public class SecurityConfig {

    @Autowired
    JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        request -> {
                            request
//                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/admin/**", "/hotels/**", "/booking/**").authenticated()
                                    .anyRequest().permitAll();

                        })
//                .httpBasic(Customizer.withDefaults())
                // TODO::
                // this is for postman but not needed to us as we are using customised jwt
                // The client sends a username and password with every request.
                // These credentials are sent in the Authorization header in Base64-encoded format:
//                .formLogin(Customizer.withDefaults())
                // this is for web browser
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // jwtAuthFilter customised by us, should be executed before UsernamePasswordAuthenticationFilter
                .csrf(AbstractHttpConfigurer :: disable);
                // csrf(csrfConfig -> csrfConfig.disable()) both are same, first one is method reference

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }




}