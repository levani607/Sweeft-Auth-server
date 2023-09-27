package me.levani.authorizationserver.model.flow;

import me.levani.authorizationserver.model.core.ExecutionRequest;
import me.levani.authorizationserver.model.core.TokenSigner;
import me.levani.authorizationserver.model.domain.Execution;
import me.levani.authorizationserver.model.domain.FlowExecution;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.repository.FlowExecutionRepository;
import me.levani.authorizationserver.service.ExecutionService;
import me.levani.authorizationserver.utils.ParserUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseRequestChainManager {

    private final ExecutionService executionService;

    private final FlowExecutionRepository flowExecutionRepository;
    private final List<ExecutionRequest> executionRequests;
    private final TokenSigner tokenSigner;


    @PostConstruct
    public void setUp() {
        List<Execution> executions = new ArrayList<>();
        List<Execution> all = executionService.findAll();
        Map<String, Execution> executionMap = all.stream().collect(Collectors.toMap(Execution::getExecutionName, j -> j));
        for (ExecutionRequest executionService : executionRequests) {
            Execution execution = executionMap.remove(executionService.getName());
            if (execution == null) {
                Execution newExecution = new Execution();
                newExecution.setExecutionName(executionService.getName());
                newExecution.setEntityStatus(EntityStatus.ACTIVE);
                executions.add(newExecution);
            }else{

                executions.add(execution);
            }
        }
        Collection<Execution> leftValues = executionMap.values();
        for (Execution leftValue : leftValues) {
            leftValue.setEntityStatus(EntityStatus.DELETED);
            executions.add(leftValue);
        }
        executionService.saveAll(executions);
    }

    public void startRequest(String realm,PayloadResponse payloadResponse, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String grantName = ParserUtils.extractGrantType(servletRequest);
        String clientId = ParserUtils.extractClientId(servletRequest);
        //OrderQueryByOrderId
        List<FlowExecution> flowExecutions = flowExecutionRepository.findByClientIdAndGrantType(realm,clientId, grantName);
        Map<String, ExecutionRequest> collect = executionRequests.stream().collect(Collectors.toMap(ExecutionRequest::getName, j -> j));


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
        tokenSigner.signToken(payloadResponse, 2L);
    }


}
