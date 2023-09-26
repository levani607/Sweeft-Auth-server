package com.example.authorizationserver.model.request;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private Long realmId;
    private String firstname;
    private String lastname;
    private String middleName;
}
