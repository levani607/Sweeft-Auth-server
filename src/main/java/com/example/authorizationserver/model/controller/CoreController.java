package com.example.authorizationserver.model.controller;

import com.example.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoreController {



    @PostMapping(
            value = "/realms/{realmName}/",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void validate(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

    }

}
