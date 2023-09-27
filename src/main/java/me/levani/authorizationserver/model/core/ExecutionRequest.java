package me.levani.authorizationserver.model.core;

import me.levani.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ExecutionRequest {

    String getName();
    void doFilter(HttpServletRequest request, HttpServletResponse response, SecureRequestChain chain, PayloadResponse payloadResponse);

}
