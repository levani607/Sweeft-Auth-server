package com.example.authorizationserver.service;

import com.example.authorizationserver.model.core.TokenSigner;
import com.example.authorizationserver.model.domain.KeyStoreModel;
import com.example.authorizationserver.model.domain.Realm;
import com.example.authorizationserver.model.response.HeaderResponse;
import com.example.authorizationserver.model.response.PayloadResponse;
import com.example.authorizationserver.model.response.SecureKeyResponse;
import com.example.authorizationserver.model.response.SignedTokenResponse;
import com.example.authorizationserver.repository.RealmRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.dh.KeyPairGeneratorSpi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Date;
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
