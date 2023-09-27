package me.levani.authorizationserver.repository;

import me.levani.authorizationserver.model.domain.KeyStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyStoreModelRepository extends JpaRepository<KeyStoreModel,Long> {


}
