package com.demo.security;

import com.demo.security.auth.domain.RegisterRequest;
import com.demo.security.auth.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.demo.security.auth.domain.enums.Role.ADMIN;
import static com.demo.security.auth.domain.enums.Role.USER;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
