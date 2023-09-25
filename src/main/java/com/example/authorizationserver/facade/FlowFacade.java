package com.example.authorizationserver.facade;


import com.example.authorizationserver.mappers.FlowMapper;
import com.example.authorizationserver.model.domain.Client;
import com.example.authorizationserver.model.domain.Execution;
import com.example.authorizationserver.model.domain.Flow;
import com.example.authorizationserver.model.domain.FlowExecution;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.request.FlowExecutionRequest;
import com.example.authorizationserver.model.request.FlowRequest;
import com.example.authorizationserver.service.ClientService;
import com.example.authorizationserver.service.ExecutionService;
import com.example.authorizationserver.service.FlowExecutionService;
import com.example.authorizationserver.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowFacade {

    private final FlowExecutionService flowExecutionService;
    private final FlowService flowService;
    private final ExecutionService executionService;
    private final ClientService clientService;

    @Transactional
    public void create(FlowRequest flowRequest) {
        Client client = clientService.findById(flowRequest.getClientId());
        Flow flow = FlowMapper.mapToFlow(flowRequest);
        flow.setClient(client);
        flowService.save(flow);
        List<FlowExecutionRequest> flowExecutions = flowRequest.getFlowExecutions();
        List<Long> ids = flowExecutions.stream().map(FlowExecutionRequest::getExecutionId).toList();
        Map<Long, Execution> executionMap = executionService.findByIdIn(ids).stream().collect(Collectors.toMap(Execution::getId, j -> j));

        List<FlowExecution> executionsToSave=new ArrayList<>();
        for (FlowExecutionRequest flowExecutionRequest : flowExecutions) {
            Execution execution = executionMap.get(flowExecutionRequest.getExecutionId());
            if(execution==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            FlowExecution flowExecution = new FlowExecution();
            flowExecution.setFlow(flow);
            flowExecution.setEntityStatus(EntityStatus.ACTIVE);
            flowExecution.setOrderId(flowExecutionRequest.getOrderId());
            flowExecution.setExecution(execution);

        }
        flowExecutionService.saveAll(executionsToSave);
    }
}
