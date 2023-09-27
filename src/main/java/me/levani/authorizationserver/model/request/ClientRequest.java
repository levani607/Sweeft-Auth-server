package me.levani.authorizationserver.model.request;

import lombok.Data;

@Data
public class ClientRequest {

    private String clientName;
    private Long realmId;

}
