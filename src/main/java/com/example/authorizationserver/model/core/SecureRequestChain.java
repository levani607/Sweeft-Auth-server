package com.example.authorizationserver.model.core;

import com.example.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SecureRequestChain {



    void doFilter( HttpServletRequest request, HttpServletResponse response,PayloadResponse secureRequest );
     void start(HttpServletRequest request, HttpServletResponse response, PayloadResponse payloadResponse);
}
