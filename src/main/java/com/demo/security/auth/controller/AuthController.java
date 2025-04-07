package com.demo.security.auth.controller;


import com.demo.security.auth.domain.RegisterRequest;
import com.demo.security.auth.domain.dto.AuthenticationRequest;
import com.demo.security.auth.domain.dto.AuthenticationResponse;
import com.demo.security.auth.domain.dto.EmailVerificationRequest;
import com.demo.security.auth.domain.dto.EmailVerificationResponse;
import com.demo.security.auth.service.AuthenticationService;
import com.demo.security.auth.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticateRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticateRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<EmailVerificationResponse> verifyEmail(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        EmailVerificationResponse emailVerificationResponse = authenticationService.verifyEmail(emailVerificationRequest);
        return new ResponseEntity<>(emailVerificationResponse, HttpStatus.OK);
    }

    @PostMapping("/resend-verification-code")
    public ResponseEntity<EmailVerificationResponse> resendVerificationCode(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        EmailVerificationResponse emailVerificationResponse = authenticationService.resendVerificationCode(emailVerificationRequest.getEmail());
        return new ResponseEntity<>(emailVerificationResponse, HttpStatus.OK);
    }
}
