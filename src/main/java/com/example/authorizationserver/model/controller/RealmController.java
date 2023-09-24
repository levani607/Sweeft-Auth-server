package com.example.authorizationserver.model.controller;

import com.example.authorizationserver.facade.RealmFacade;
import com.example.authorizationserver.model.request.RealmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-api/realm")
public class RealmController {

    private final RealmFacade realmFacade;

    @PostMapping
    public void createRealm(@RequestBody RealmRequest realmRequest) {
        realmFacade.saveRealm(realmRequest);
    }
}
