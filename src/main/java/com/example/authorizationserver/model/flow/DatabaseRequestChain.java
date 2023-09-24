package com.example.authorizationserver.model.flow;

import com.example.authorizationserver.model.core.ExecutionRequest;
import com.example.authorizationserver.model.core.SecureRequestChain;
import com.example.authorizationserver.model.core.TokenSigner;
import com.example.authorizationserver.model.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class DatabaseRequestChain implements SecureRequestChain {


    private final Iterator<ExecutionRequest> chain;


    public DatabaseRequestChain(Iterable<ExecutionRequest> chain) {
        this.chain = chain.iterator();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, PayloadResponse payloadResponse) {
        if (chain.hasNext()) {
            ExecutionRequest next = chain.next();
            next.doFilter(request, response, this, payloadResponse);
        } else {
            log.info("Signed!");
        }

    }

    @Override
    public void start(HttpServletRequest request, HttpServletResponse response, PayloadResponse payloadResponse) {

        if (chain.hasNext()) {
            ExecutionRequest next = chain.next();
            next.doFilter(request, response, this, payloadResponse);
        }

    }

}
