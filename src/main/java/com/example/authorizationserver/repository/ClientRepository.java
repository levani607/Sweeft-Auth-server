package com.example.authorizationserver.repository;

import com.example.authorizationserver.model.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository  extends JpaRepository<Client,Long> {
}
