package me.levani.authorizationserver.service;

import me.levani.authorizationserver.exeption.CustomHttpStatus;
import me.levani.authorizationserver.exeption.ServerException;
import me.levani.authorizationserver.model.domain.FlowExecution;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.repository.FlowExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowExecutionService {
    private final FlowExecutionRepository flowExecutionRepository;

    public FlowExecution findById(Long id) {
        return flowExecutionRepository.findByIdAndStatus(id, EntityStatus.ACTIVE)
                .orElseThrow(() -> new ServerException(CustomHttpStatus.BAD_REQUEST,"Client Not found"));
    }
    public FlowExecution save(FlowExecution flowExecution){
        return flowExecutionRepository.save(flowExecution);
    }

    public void saveAll(List<FlowExecution> flowExecution) {
        flowExecutionRepository.saveAll(flowExecution);
    }
}
