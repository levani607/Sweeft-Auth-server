package me.levani.authorizationserver.service;

import me.levani.authorizationserver.model.core.TokenSigner;
import me.levani.authorizationserver.model.domain.KeyStoreModel;
import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.response.HeaderResponse;
import me.levani.authorizationserver.model.response.PayloadResponse;
import me.levani.authorizationserver.model.response.SecureKeyResponse;
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
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultTokenSigner implements TokenSigner {

    private final RealmRepository realmRepository;
    private final ObjectMapper objectMapper;

    private final Environment env;

    @Override
    @Transactional
    public SignedTokenResponse signToken(PayloadResponse payloadResponse, Long realmId) {
        Realm realm = realmRepository.findById(realmId)
                .orElseThrow();
        KeyStoreModel keyStoreModel = realm.getKeyStoreModel();
//

        try {
            KeyStore jks = KeyStore.getInstance("JKS");
            jks.load(new ByteArrayInputStream(keyStoreModel.getKeyStore()), Objects.requireNonNull(env.getProperty("auth.server.keystore.password")).toCharArray());
            KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(Objects.requireNonNull(env.getProperty("auth.server.key.password")).toCharArray());
            KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) jks.getEntry(keyStoreModel.getKid(), protectionParameter);
            PrivateKey privateKey = entry.getPrivateKey();
            HeaderResponse headerResponse = buildHeaders("RS256", keyStoreModel.getKid());

            signKey(payloadResponse, headerResponse, privateKey, entry.getCertificate().getPublicKey());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    private String signKey(PayloadResponse payloadResponse, HeaderResponse headerResponse, PrivateKey privateKey, PublicKey publicKey) {
        try {
            byte[] header = objectMapper.writeValueAsString(headerResponse).replace(",",",\n").getBytes(StandardCharsets.UTF_8);
            byte[] payload = objectMapper.writeValueAsString(payloadResponse).replace(",",",\n").getBytes(StandardCharsets.UTF_8);
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
            boolean isSignatureValid = sig.verify(Base64.decodeBase64URLSafe(signatureString));
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


    @Override
    public List<SecureKeyResponse> getKeys() {
        return null;
    }
}
