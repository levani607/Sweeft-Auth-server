package com.example.authorizationserver.controller.service;

import com.example.authorizationserver.facade.RealmFacade;
import com.example.authorizationserver.model.request.RealmRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/service-api/realm")
@RequiredArgsConstructor
@RestController
public class RealmController {

    private final RealmFacade realmFacade;

    @PostMapping
    public void create(@RequestBody @Valid  RealmRequest request) {
        realmFacade.saveRealm(request);
    }
}
