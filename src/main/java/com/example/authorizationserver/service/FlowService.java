package com.example.authorizationserver.service;

import com.example.authorizationserver.model.domain.Flow;
import com.example.authorizationserver.model.domain.FlowExecution;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.repository.FlowExecutionRepository;
import com.example.authorizationserver.repository.FlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowService {

    private final FlowRepository flowRepository;

    public Flow findById(Long id) {
        return flowRepository.findByIdAndStatus(id, EntityStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }


    public Flow save(Flow flowExecution){
        return flowRepository.save(flowExecution);
    }
}
