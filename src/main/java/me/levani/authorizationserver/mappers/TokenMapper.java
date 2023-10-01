package me.levani.authorizationserver.mappers;

import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.model.response.SignedTokenResponse;

public class TokenMapper {

    public static SignedTokenResponse response(String token, PayloadResponse response){
        SignedTokenResponse signedTokenResponse = new SignedTokenResponse();
        signedTokenResponse.setToken(token);
        signedTokenResponse.setExpiresIn(response.getExp()-response.getIat());
        return signedTokenResponse;
    }
}
