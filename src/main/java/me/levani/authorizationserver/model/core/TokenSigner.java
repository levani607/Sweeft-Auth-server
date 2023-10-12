package me.levani.authorizationserver.model.core;

import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.model.response.SignedTokenResponse;

import java.util.List;

public interface TokenSigner{

    SignedTokenResponse signToken(PayloadResponse payloadResponse, Long realmId);

}
