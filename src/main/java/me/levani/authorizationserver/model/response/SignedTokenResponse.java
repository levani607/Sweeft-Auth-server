package me.levani.authorizationserver.model.response;

import lombok.Data;

@Data
public class SignedTokenResponse {

    private String token;

    private Long expiresIn;

    private String refreshToken;

    private Long refreshExpiresIn;
}
