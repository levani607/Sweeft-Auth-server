package me.levani.authorizationserver.model.core;

import me.levani.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SecureRequestChain {



    void doFilter( HttpServletRequest request, HttpServletResponse response,PayloadResponse secureRequest );
     void start(HttpServletRequest request, HttpServletResponse response, PayloadResponse payloadResponse);
}
