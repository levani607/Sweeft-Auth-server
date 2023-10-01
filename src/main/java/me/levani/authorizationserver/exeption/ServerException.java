package me.levani.authorizationserver.exeption;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
@Data
@EqualsAndHashCode(callSuper = false)
public class ServerException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public ServerException(CustomHttpStatus status, String message) {
        this.status = status.getStatus();
        this.message = message;
    }

    public ServerException(CustomHttpStatus status) {
        this.status = status.getStatus();
        this.message = status.getMessage();
    }
}
