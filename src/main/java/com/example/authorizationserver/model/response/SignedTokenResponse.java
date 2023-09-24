package com.example.authorizationserver.model.response;

import lombok.Data;

import java.security.PrivateKey;

@Data
public class SignedTokenResponse {

    private String jwt;

    private Long expiresIn;

    private String refreshToken;

    private Long refreshExpiresIn;
}
