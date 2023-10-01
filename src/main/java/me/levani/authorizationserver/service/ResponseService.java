package me.levani.authorizationserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.levani.authorizationserver.exeption.CustomHttpStatus;
import me.levani.authorizationserver.exeption.ServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.io.PrintWriter;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ObjectMapper mapper;

    public void sendResponse(HttpServletResponse servletResponse, Object payloadResponse) {
        try(PrintWriter writer = servletResponse.getWriter()) {
            String response = mapper.writeValueAsString(payloadResponse);
            servletResponse.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
            servletResponse.setStatus(HttpStatus.OK.value());
            writer.println(response);
            writer.flush();
        }catch (Exception e){
        throw new ServerException(CustomHttpStatus.BAD_REQUEST);
        }

    }
}
