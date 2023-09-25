package com.example.authorizationserver.model.request;

import lombok.Data;

@Data
public class FlowExecutionRequest {

    private Long executionId;
    private Integer orderId;
}
