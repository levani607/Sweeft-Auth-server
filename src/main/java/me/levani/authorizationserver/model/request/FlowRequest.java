package me.levani.authorizationserver.model.request;

import me.levani.authorizationserver.model.annotation.FlowExecutionValidator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data

public class FlowRequest {

    @NotNull(message = "grantName can not be null;")
    private String grantName;

    @NotNull(message = "flowName can not be null;")
    private String flowName;

    @NotNull(message = "clientId can not be null;")
    private Long clientId;
    @FlowExecutionValidator
    private List<FlowExecutionRequest> flowExecutions;

}
