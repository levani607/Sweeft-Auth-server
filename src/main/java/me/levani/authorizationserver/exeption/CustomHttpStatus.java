package me.levani.authorizationserver.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum CustomHttpStatus {

    BAD_REQUEST("BAD_REQUEST",HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR",HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
    @Getter
    private String message;
    @Getter
    private HttpStatus status;

    CustomHttpStatus(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
