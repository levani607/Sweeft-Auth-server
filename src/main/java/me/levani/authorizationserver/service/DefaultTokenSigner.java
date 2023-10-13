package me.levani.authorizationserver.service;

import me.levani.authorizationserver.exeption.CustomHttpStatus;
import me.levani.authorizationserver.exeption.ServerException;
import me.levani.authorizationserver.mappers.TokenMapper;
import me.levani.authorizationserver.model.core.RefreshTokenGenerator;
import me.levani.authorizationserver.model.core.TokenSigner;
import me.levani.authorizationserver.model.domain.KeyStoreModel;
import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.response.HeaderResponse;
import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.model.response.PrivateKeyEntryResponse;
import me.levani.authorizationserver.model.response.SignedTokenResponse;
import me.levani.authorizationserver.repository.RealmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultTokenSigner implements TokenSigner {

//    private final RealmRepository realmRepository;
    private final ObjectMapper objectMapper;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final KeyStoreService keyStoreService;

    @Override
    @Transactional
    public SignedTokenResponse signToken(PayloadResponse payloadResponse, Long realmId) {

        try {
            PrivateKeyEntryResponse entryResponse = keyStoreService.getKeys(realmId);
            KeyStore.PrivateKeyEntry entry = entryResponse.getKeyEntry();
            PrivateKey privateKey = entry.getPrivateKey();
            HeaderResponse headerResponse = buildHeaders("RS256", entryResponse.getKid());
            payloadResponse.setIat(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            payloadResponse.setExp(LocalDateTime.now().plusMinutes(5).toEpochSecond(ZoneOffset.UTC));
            payloadResponse.setJti(UUID.randomUUID());
            String token = signKey(payloadResponse, headerResponse, privateKey, entry.getCertificate().getPublicKey());
            SignedTokenResponse response = TokenMapper.response(token, payloadResponse);
            refreshTokenGenerator.insertRefreshToken(payloadResponse,response,realmId);
            return response;
        } catch (Exception e) {
            throw new ServerException(CustomHttpStatus.BAD_REQUEST, "Failed to sign token!;");
        }
    }

    private String signKey(PayloadResponse payloadResponse, HeaderResponse headerResponse, PrivateKey privateKey, PublicKey publicKey) {
        try {
            byte[] header = objectMapper.writeValueAsString(headerResponse).replace(",", ",\n").getBytes(StandardCharsets.UTF_8);
            byte[] payload = objectMapper.writeValueAsString(payloadResponse).replace(",", ",\n").getBytes(StandardCharsets.UTF_8);
            String headerEncoded = Base64.encodeBase64URLSafeString(header);
            String payloadEncoded = Base64.encodeBase64URLSafeString(payload);
            String headerPayload = headerEncoded + "." + payloadEncoded;
            byte[] data = headerPayload.getBytes(StandardCharsets.US_ASCII);
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privateKey);
            sig.update(data);
            String signatureString = Base64.encodeBase64URLSafeString(sig.sign());
            String finalPayload = headerPayload + "." + signatureString;
            sig.initVerify(publicKey);
            sig.update(headerPayload.getBytes());
            return finalPayload;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private HeaderResponse buildHeaders(String algorithm, String kid) {
        return HeaderResponse
                .builder()
                .alg(algorithm)
                .kid(kid)
                .typ("JWT")
                .build();
    }


}
