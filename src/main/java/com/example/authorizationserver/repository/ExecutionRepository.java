package com.example.authorizationserver.repository;

import com.example.authorizationserver.model.domain.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution,Long> {
}
