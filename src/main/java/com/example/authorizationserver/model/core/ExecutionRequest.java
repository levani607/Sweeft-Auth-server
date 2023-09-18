package com.example.authorizationserver.model.core;

import com.example.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ExecutionRequest {

    String getName();
    void doFilter(HttpServletRequest request, HttpServletResponse response, SecureRequestChain chain, PayloadResponse payloadResponse);

}
