package com.example.authorizationserver.controller.service;

import com.example.authorizationserver.facade.UserFacade;
import com.example.authorizationserver.model.request.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-api/user")
public class UserController {


    private  final UserFacade userFacade;
    @PostMapping
    public void create(@RequestBody @Valid UserRequest request){
        userFacade.saveUser(request);
    }
}
