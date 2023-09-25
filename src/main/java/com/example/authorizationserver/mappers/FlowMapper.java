package com.example.authorizationserver.mappers;

import com.example.authorizationserver.model.domain.Flow;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.request.FlowRequest;

public class FlowMapper {

    public static Flow mapToFlow(FlowRequest flowRequest) {
        Flow flow = new Flow();
        flow.setFlowName(flowRequest.getFlowName());
        flow.setEntityStatus(EntityStatus.ACTIVE);
        flow.setGrantName(flowRequest.getGrantName());
        flow.setIsActive(false);
        return flow;
    }
}
