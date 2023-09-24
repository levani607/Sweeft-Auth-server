package com.example.authorizationserver.model.core;

import com.example.authorizationserver.model.response.PayloadResponse;
import com.example.authorizationserver.model.response.SecureKeyResponse;
import com.example.authorizationserver.model.response.SignedTokenResponse;

import java.util.List;

public interface TokenSigner{

    SignedTokenResponse signToken(PayloadResponse payloadResponse,Long realmId);

    List<SecureKeyResponse> getKeys();
}
