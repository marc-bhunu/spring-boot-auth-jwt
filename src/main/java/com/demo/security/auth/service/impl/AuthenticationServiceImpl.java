package com.demo.security.auth.service.impl;

import com.demo.security.auth.domain.RegisterRequest;
import com.demo.security.auth.domain.dto.AuthenticationRequest;
import com.demo.security.auth.domain.dto.AuthenticationResponse;
import com.demo.security.auth.domain.dto.EmailVerificationRequest;
import com.demo.security.auth.domain.dto.EmailVerificationResponse;
import com.demo.security.auth.domain.entities.User;
import com.demo.security.auth.domain.enums.Role;
import com.demo.security.auth.reposity.UserRepository;
import com.demo.security.auth.service.AuthenticationService;
import com.demo.security.auth.service.EmailService;
import com.demo.security.config.JwtService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    @Value("${security.jwt.expiration-time}")
    private String tokenExpirationTime;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(15);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public void sendVerificationEmail(User user) {
        String subject = "Verify Email Address";
        String verificationCode = user.getVerificationCode();

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, user.getVerificationCode());
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .enabled(false)
                .verificationCode(generateVerificationCode())
                .verificationCodeExpiryDate(calculateExpiryDate())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        sendVerificationEmail(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .tokenExpiry(tokenExpirationTime)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getEmail(),
                        authenticateRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticateRequest.getEmail())
                .orElseThrow();
        if (!user.isEnabled()) {
            throw new IllegalStateException("Email not verified");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    @Override
    public EmailVerificationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest) {

        User user = userRepository.findByEmail(emailVerificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + emailVerificationRequest.getEmail()));

        if (user.isEnabled()) {
            throw new IllegalStateException("Email already verified");
        }

        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(emailVerificationRequest.getVerificationCode())) {
            throw new IllegalArgumentException("Invalid verification code");
        }

        if (user.getVerificationCodeExpiryDate() == null ||
                LocalDateTime.now().isAfter(user.getVerificationCodeExpiryDate())) {
            throw new IllegalArgumentException("Verification code has expired");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiryDate(null);
        userRepository.save(user);

        return EmailVerificationResponse.builder()
                .message("Email verified successfully")
                .email(user.getEmail())
                .verified(true)
                .build();
    }

    @Override
    public EmailVerificationResponse resendVerificationCode(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEnabled()) {
            throw new IllegalStateException("Email already verified");
        }

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiryDate(calculateExpiryDate());

        userRepository.save(user);

        sendVerificationEmail(user);

        EmailVerificationResponse emailVerificationResponse = EmailVerificationResponse.builder()
                .email(user.getEmail())
                .verified(false)
                .message("Verification code resent successfully")
                .build();

        return emailVerificationResponse;
    }


}
