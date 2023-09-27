package me.levani.authorizationserver.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HeaderResponse {
    private String alg;
    private String typ;
    private String kid;
}
