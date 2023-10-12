package me.levani.authorizationserver.model.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PayloadResponse {

    private String subject;
    private UUID jti;
    private String username;
    private String name;
    private String lastname;
    private String middleName;
    private String email;
    private Long iat;
    private Long exp;

}
