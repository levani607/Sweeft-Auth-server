package com.example.authorizationserver.controller.service;

import com.example.authorizationserver.facade.FlowFacade;
import com.example.authorizationserver.model.request.FlowRequest;
import com.example.authorizationserver.model.request.RealmRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/service-api/flow")
@RequiredArgsConstructor
@RestController
public class FlowController {

    private final FlowFacade flowFacade;

    @PostMapping
    public void create(@RequestBody @Valid FlowRequest request) {
        flowFacade.create(request);
    }
}
