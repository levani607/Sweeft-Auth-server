package me.levani.authorizationserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.KeyStore;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateKeyEntryResponse {
    private KeyStore.PrivateKeyEntry keyEntry;
    private String kid;
}
