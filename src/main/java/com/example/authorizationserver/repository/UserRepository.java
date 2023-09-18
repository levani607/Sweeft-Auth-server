package com.example.authorizationserver.repository;

import com.example.authorizationserver.model.domain.RealmUser;
import com.example.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<RealmUser, UUID> {

    @Query("select u from RealmUser u where u.realm.realmName = :realmName and u.username=:username and u.entityStatus = :status")
    Optional<RealmUser> findByUsernameAndRealmName(String username, String realmName, EntityStatus status);
}
