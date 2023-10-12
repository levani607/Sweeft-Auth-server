package me.levani.authorizationserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.levani.authorizationserver.exeption.CustomHttpStatus;
import me.levani.authorizationserver.exeption.ServerException;
import me.levani.authorizationserver.model.core.CertificateManager;
import me.levani.authorizationserver.model.domain.KeyStoreModel;
import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.response.CertificateResponseBody;
import me.levani.authorizationserver.repository.RealmRepository;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CertificateManagerImpl implements CertificateManager {
    private final RealmRepository realmRepository;

    private final Environment env;

    @Override
    @Transactional
    public CertificateResponseBody getCertificates(String realms) {
        Realm realm = realmRepository.findByRealmNameAndEntityStatus(realms, EntityStatus.ACTIVE)
                .orElseThrow();
        KeyStoreModel keyStoreModel = realm.getKeyStoreModel();

        try {
            KeyStore jks = KeyStore.getInstance("JKS");
            jks.load(new ByteArrayInputStream(keyStoreModel.getKeyStore()), Objects.requireNonNull(env.getProperty("auth.server.keystore.password")).toCharArray());
            KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(Objects.requireNonNull(env.getProperty("auth.server.key.password")).toCharArray());
            KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) jks.getEntry(keyStoreModel.getKid(), protectionParameter);
            X509Certificate certificate = (X509Certificate) entry.getCertificate();
            RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();

            CertificateResponseBody responseBody = new CertificateResponseBody();
            CertificateResponseBody.CertificateKeys certificateKeys = new CertificateResponseBody.CertificateKeys();
            certificateKeys.setAlg(certificate.getSigAlgName());
            certificateKeys.setKty(publicKey.getAlgorithm());
            certificateKeys.setX5t(Base64.toBase64String(certificate.getSignature()));
            certificateKeys.setX5c(List.of(Base64.toBase64String(certificate.getEncoded())));
            certificateKeys.setE(Base64.toBase64String(publicKey.getPublicExponent().toByteArray()));
            certificateKeys.setN(Base64.toBase64String(publicKey.getModulus().toByteArray()));
            certificateKeys.setUse("sig");
            certificateKeys.setKid(keyStoreModel.getKid());
            responseBody.setKeys(certificateKeys);
            return responseBody;
        } catch (Exception e) {
            throw new ServerException(CustomHttpStatus.BAD_REQUEST);
        }
    }
}
