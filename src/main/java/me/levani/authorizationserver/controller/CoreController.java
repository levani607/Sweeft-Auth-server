package me.levani.authorizationserver.controller;

import me.levani.authorizationserver.model.flow.DatabaseRequestChainManager;
import me.levani.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoreController {

    private final DatabaseRequestChainManager manager;

    @PostMapping(
            value = "realms/{realm}/token"
    )
    public void validate(
            @PathVariable("realm")String realm,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        manager.startRequest(realm,new PayloadResponse(), request, response);
    }


}
