package com.example.authorizationserver.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

@Entity
@Data
public class KeyStoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    private String kid;

    @Lob
    @ToString.Exclude
    private byte[] keyStore;
}
