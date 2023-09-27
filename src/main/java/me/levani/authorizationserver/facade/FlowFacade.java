package me.levani.authorizationserver.facade;


import me.levani.authorizationserver.mappers.FlowMapper;
import me.levani.authorizationserver.model.domain.Client;
import me.levani.authorizationserver.model.domain.Execution;
import me.levani.authorizationserver.model.domain.Flow;
import me.levani.authorizationserver.model.domain.FlowExecution;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.request.FlowExecutionRequest;
import me.levani.authorizationserver.model.request.FlowRequest;
import me.levani.authorizationserver.service.ClientService;
import me.levani.authorizationserver.service.ExecutionService;
import me.levani.authorizationserver.service.FlowExecutionService;
import me.levani.authorizationserver.service.FlowService;
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
        executionsToSave.add(flowExecution);
        }
        flowExecutionService.saveAll(executionsToSave);
    }
}
