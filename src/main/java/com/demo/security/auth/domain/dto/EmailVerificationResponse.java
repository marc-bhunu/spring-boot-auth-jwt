package com.demo.security.auth.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVerificationResponse {
    private String email;
    private boolean verified;
    private String message;

}
