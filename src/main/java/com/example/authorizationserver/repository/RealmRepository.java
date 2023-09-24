package com.example.authorizationserver.repository;

import com.example.authorizationserver.model.domain.Realm;
import com.example.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealmRepository extends JpaRepository<Realm,Long> {

    boolean existsByRealmNameAndEntityStatus(String name, EntityStatus status);
    Optional<Realm> findByIdAndEntityStatus(Long id, EntityStatus status);
}
