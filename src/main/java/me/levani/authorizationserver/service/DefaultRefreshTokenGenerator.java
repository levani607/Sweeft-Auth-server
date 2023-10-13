package me.levani.authorizationserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.levani.authorizationserver.model.core.RefreshTokenGenerator;
import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.model.response.PrivateKeyEntryResponse;
import me.levani.authorizationserver.model.response.RefreshTokenResponse;
import me.levani.authorizationserver.model.response.SignedTokenResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.Signature;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultRefreshTokenGenerator implements RefreshTokenGenerator {

    private final ObjectMapper objectMapper;
    private final KeyStoreService keyStoreService;
    @Override
    @SneakyThrows
    public void insertRefreshToken(PayloadResponse payloadResponse, SignedTokenResponse tokenResponse,Long realmId)  {
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setJti(payloadResponse.getJti());
        refreshTokenResponse.setUsername(payloadResponse.getUsername());
        refreshTokenResponse.setIat(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        refreshTokenResponse.setExp(LocalDateTime.now().plusMinutes(30).toEpochSecond(ZoneOffset.UTC));
        byte[] payload = objectMapper.writeValueAsString(refreshTokenResponse).replace(",", ",\n").getBytes(StandardCharsets.UTF_8);

        String payloadEncoded = Base64.encodeBase64URLSafeString(payload);
        Signature sig = Signature.getInstance("SHA256withRSA");
        PrivateKeyEntryResponse keys = keyStoreService.getKeys(realmId);
        KeyStore.PrivateKeyEntry keyEntry = keys.getKeyEntry();
        sig.initSign(keyEntry.getPrivateKey());
        sig.update(payloadEncoded.getBytes(StandardCharsets.US_ASCII));
        String signatureString = Base64.encodeBase64URLSafeString(sig.sign());
        String finalPayload = payloadEncoded+ "."+signatureString;
        tokenResponse.setRefreshToken(finalPayload);
        tokenResponse.setRefreshExpiresIn(30L*60L);
    }
}
