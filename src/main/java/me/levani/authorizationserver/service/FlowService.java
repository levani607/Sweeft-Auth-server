package me.levani.authorizationserver.service;

import me.levani.authorizationserver.model.domain.Flow;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.repository.FlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
