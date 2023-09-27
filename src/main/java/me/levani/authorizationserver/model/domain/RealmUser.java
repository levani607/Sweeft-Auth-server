package me.levani.authorizationserver.model.domain;

import me.levani.authorizationserver.model.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RealmUser {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private String username;
    private String password;

    private String firstname;
    private String lastname;
    private String middleName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus entityStatus;
}
