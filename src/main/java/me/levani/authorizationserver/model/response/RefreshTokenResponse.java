package me.levani.authorizationserver.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class RefreshTokenResponse {

    private UUID jti;
    private String username;
    private Long iat;
    private Long exp;
}
