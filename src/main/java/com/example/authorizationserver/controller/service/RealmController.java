package com.example.authorizationserver.controller.service;

import com.example.authorizationserver.model.request.RealmRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/service-api/realm")
public class RealmController {


    @PostMapping
    public void create(RealmRequest request){

    }
}
