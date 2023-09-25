package com.example.authorizationserver.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RealmRequest {
    @NotNull(message = "realmName - can not be null!;")
    private String realmName;

}
