package me.levani.authorizationserver.service.flow;

import me.levani.authorizationserver.model.core.ExecutionRequest;
import me.levani.authorizationserver.model.core.SecureRequestChain;
import me.levani.authorizationserver.model.response.PayloadResponse;
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
