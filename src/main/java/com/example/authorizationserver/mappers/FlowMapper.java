package com.example.authorizationserver.mappers;

import com.example.authorizationserver.model.domain.Flow;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.request.FlowRequest;

public class FlowMapper {

    public static Flow mapToFlow(FlowRequest flowRequest) {
        Flow flow = new Flow();
        flow.setFlowName(flow.getFlowName());
        flow.setEntityStatus(EntityStatus.ACTIVE);
        flow.setGrantName(flow.getGrantName());
        flow.setIsActive(false);
        return flow;
    }
}
