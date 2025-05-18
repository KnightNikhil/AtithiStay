package com.nikhil.springboot.AtithiStay.security;

import com.nikhil.springboot.AtithiStay.entity.User;
import com.nikhil.springboot.AtithiStay.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

    // OncePerRequestFilter - with every request this method is executed once

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String requestHeaderToken = request.getHeader("Authorization");
            if(requestHeaderToken==null || !requestHeaderToken.startsWith("Bearer")){
                // “Pass the request and response to the next filter in the chain.”
                filterChain.doFilter(request, response);
                return;
            }
            String token = requestHeaderToken.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);

            // if userId is not null and there is no user that is authenticated "SecurityContextHolder.getContext().getAuthentication()"
            if(userId!=null && SecurityContextHolder.getContext().getAuthentication()== null){
                User user = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

            filterChain.doFilter(request, response);
        }
        catch (Exception e){
            log.info("exception::: ", e);
        }
    }
}
