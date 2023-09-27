package me.levani.authorizationserver.controller.service;

import me.levani.authorizationserver.facade.UserFacade;
import me.levani.authorizationserver.model.request.UserRequest;
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
