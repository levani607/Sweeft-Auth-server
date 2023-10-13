package me.levani.authorizationserver.model.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.model.response.SignedTokenResponse;

public interface RefreshTokenGenerator {

    void insertRefreshToken(PayloadResponse payload, SignedTokenResponse tokenResponse,Long realmId);

}
