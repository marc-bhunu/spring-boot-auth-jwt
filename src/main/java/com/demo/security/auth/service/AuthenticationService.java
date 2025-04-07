package com.demo.security.auth.service;

import com.demo.security.auth.domain.RegisterRequest;
import com.demo.security.auth.domain.dto.AuthenticationRequest;
import com.demo.security.auth.domain.dto.AuthenticationResponse;
import com.demo.security.auth.domain.dto.EmailVerificationRequest;
import com.demo.security.auth.domain.dto.EmailVerificationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticateRequest);
    EmailVerificationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest);
    EmailVerificationResponse resendVerificationCode(String email);
}
