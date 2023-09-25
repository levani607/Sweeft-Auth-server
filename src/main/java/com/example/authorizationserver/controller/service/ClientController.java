package com.example.authorizationserver.controller.service;

import com.example.authorizationserver.facade.ClientFacade;
import com.example.authorizationserver.model.request.ClientRequest;
import com.example.authorizationserver.model.request.RealmRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/service-api/client")
@RequiredArgsConstructor
@RestController
public class ClientController {

    private final ClientFacade clientFacade;

    @PostMapping
    public void create(@RequestBody @Valid  ClientRequest request) {
        clientFacade.create(request);
    }
}
