package com.nikhil.springboot.AtithiStay.security;

import com.nikhil.springboot.AtithiStay.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(("L9p4N2zA5Qv8X1lT7aEbU0kMcRsWdYzFnZxKvSgBnBo=").getBytes(StandardCharsets.UTF_8));
    }

    public String generateJWTToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                // TODO::
                // id is provided as subject
                // .subject(...) sets the identity the JWT is issued for
                // Usually set to user ID, email, or username
                // it is part of claim
                .claim("email",user.getEmail()) // claim is the data
                .claim("roles", user.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                // expiration time for JWT token
                .signWith(getSecretKey())
                //this is the secret kry provided by us
                .compact();

    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                // secret key is verified if the token provided also has the same secret key
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
