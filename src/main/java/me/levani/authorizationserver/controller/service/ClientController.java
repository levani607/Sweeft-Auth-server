package me.levani.authorizationserver.controller.service;

import me.levani.authorizationserver.facade.ClientFacade;
import me.levani.authorizationserver.model.request.ClientRequest;
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
