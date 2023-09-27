package me.levani.authorizationserver.mappers;

import me.levani.authorizationserver.model.domain.Flow;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.request.FlowRequest;

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
