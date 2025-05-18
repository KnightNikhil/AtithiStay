package com.nikhil.springboot.AtithiStay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AtithiStayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtithiStayApplication.class, args);
	}

}
