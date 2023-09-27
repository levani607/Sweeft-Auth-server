package me.levani.authorizationserver.model.response;

import lombok.Data;

@Data
public class PayloadResponse {

    private String subject;
    private String username;
    private String name;
    private String lastname;
    private String middleName;
    private String email;


}
