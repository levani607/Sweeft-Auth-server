package com.example.authorizationserver.service;

import com.example.authorizationserver.model.domain.Execution;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.repository.ExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final ExecutionRepository executionRepository;

    public Execution findById(Long id) {
        return executionRepository.findByIdAndStatus(id, EntityStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
    public List<Execution> findByIdIn(List<Long> ids) {
        return executionRepository.findByIdsInAndStatus(ids, EntityStatus.ACTIVE);
    }
    public List<Execution> findAll(){
        return executionRepository.findAllByStatus(EntityStatus.ACTIVE);
    }

    public List<Execution> saveAll(List<Execution> executions) {
        return executionRepository.saveAll(executions);
    }

}
