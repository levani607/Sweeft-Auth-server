package com.example.authorizationserver.model.flow;

import com.example.authorizationserver.model.core.ExecutionRequest;
import com.example.authorizationserver.model.core.TokenSigner;
import com.example.authorizationserver.model.domain.Execution;
import com.example.authorizationserver.model.domain.Flow;
import com.example.authorizationserver.model.domain.FlowExecution;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.response.PayloadResponse;
import com.example.authorizationserver.repository.ExecutionRepository;
import com.example.authorizationserver.repository.FlowExecutionRepository;
import com.example.authorizationserver.repository.FlowRepository;
import com.example.authorizationserver.utils.ParserUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseRequestChainManager {

    private final ExecutionRepository executionRepository;
    private final FlowRepository flowRepository;
    private final FlowExecutionRepository flowExecutionRepository;
    private final List<ExecutionRequest> executionServices;
    private final TokenSigner tokenSigner;


    @PostConstruct
    public void setUp() {
        List<Execution> executions = new ArrayList<>();
        for (ExecutionRequest executionService : executionServices) {
            Execution execution = new Execution();
            execution.setExecutionName(executionService.getName());
            execution.setEntityStatus(EntityStatus.ACTIVE);
            //Todo add logic to remove old executions
            executions.add(execution);
        }
        executionRepository.saveAll(executions);
    }

    public void startRequest(PayloadResponse payloadResponse, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String grantName =ParserUtils.extractClientId(servletRequest) ;
        String clientId = ParserUtils.extractClientId(servletRequest);
        //OrderQueryByOrderId
        List<FlowExecution> flowExecutions = flowExecutionRepository.findByClientIdAndGrantType(clientId, grantName);
        Map<String, ExecutionRequest> collect = executionServices.stream().collect(Collectors.toMap(ExecutionRequest::getName, j -> j));


        List<ExecutionRequest> requestSToPass = new ArrayList<>();
        for (FlowExecution flowExecution : flowExecutions) {
            Execution execution = flowExecution.getExecution();
            if (!collect.containsKey(execution.getExecutionName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            ExecutionRequest executionRequest = collect.get(execution.getExecutionName());
            requestSToPass.add(executionRequest);
        }
        DatabaseRequestChain databaseRequestChain = new DatabaseRequestChain(requestSToPass);
        databaseRequestChain.start(servletRequest, servletResponse, payloadResponse);
        tokenSigner.signToken(payloadResponse,null);
    }


}
