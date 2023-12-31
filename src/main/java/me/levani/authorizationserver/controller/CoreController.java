package me.levani.authorizationserver.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.levani.authorizationserver.model.response.CertificateResponseBody;
import me.levani.authorizationserver.service.flow.DatabaseRequestChainManager;
import me.levani.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CoreController {

    private final DatabaseRequestChainManager manager;

    @PostConstruct
    public void bla() {
        log.info("Core controller created!");

    }

    @PostMapping(
            value = "realms/{realm}/token"
    )
    public void token(
            @PathVariable("realm") String realm,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        manager.startRequest(realm, new PayloadResponse(), request, response);
    }

    @GetMapping(value = "realms/{realm}/certs")
    public CertificateResponseBody certs(
            @PathVariable("realm") String realm
    ) {
        return manager.getCertificates(realm);
    }
}
