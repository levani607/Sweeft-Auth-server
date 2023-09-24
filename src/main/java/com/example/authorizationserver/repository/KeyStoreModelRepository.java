package com.example.authorizationserver.repository;

import com.example.authorizationserver.model.domain.KeyStoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyStoreModelRepository extends JpaRepository<KeyStoreModel,Long> {


}
