package com.nikhil.springboot.AtithiStay.repository;

import com.nikhil.springboot.AtithiStay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
