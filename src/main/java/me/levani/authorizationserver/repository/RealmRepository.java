package me.levani.authorizationserver.repository;

import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealmRepository extends JpaRepository<Realm,Long> {

    boolean existsByRealmNameAndEntityStatus(String name, EntityStatus status);
    Optional<Realm> findByIdAndEntityStatus(Long id, EntityStatus status);
}
