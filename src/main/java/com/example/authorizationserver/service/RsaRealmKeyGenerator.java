package com.example.authorizationserver.service;

import com.example.authorizationserver.model.core.RealmKeyGenerator;
import com.example.authorizationserver.model.domain.KeyStoreModel;
import com.example.authorizationserver.model.domain.Realm;
import com.example.authorizationserver.repository.KeyStoreModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RsaRealmKeyGenerator implements RealmKeyGenerator {
    private final Environment env;

    public void createKey(Realm realm) {
        try {
            KeyStoreModel keyStoreModel = new KeyStoreModel();
            String kid = RandomStringUtils.randomAlphanumeric(16);
            keyStoreModel.setKid(kid);
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(Integer.parseInt(Objects.requireNonNull(env.getProperty("auth.server.key.length"))));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            X509Certificate x509 = getX509Certificate(keyPairGenerator, keyPair, realm.getRealmName());

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, Objects.requireNonNull(env.getProperty("auth.server.keystore.password")).toCharArray());
            KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(Objects.requireNonNull(env.getProperty("auth.server.key.password")).toCharArray());

            // Create a PrivateKeyEntry object
            KeyStore.PrivateKeyEntry privateKeyEntry = new KeyStore.PrivateKeyEntry(keyPair.getPrivate(), new Certificate[]{x509});

            // Set the KeyStore entry
            keyStore.setEntry(kid, privateKeyEntry, protectionParameter);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            keyStore.store(stream, Objects.requireNonNull(env.getProperty("auth.server.keystore.password")).toCharArray());

            keyStoreModel.setKeyStore(stream.toByteArray());
            realm.setKeyStoreModel(keyStoreModel);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    private X509Certificate getX509Certificate(KeyPairGenerator keyPairGenerator, KeyPair keyPair, String realmName) throws OperatorCreationException, CertificateException {
        X500Name X500Name = new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.CN, realmName)
                .build();

        JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(X500Name, BigInteger.valueOf(System.currentTimeMillis()), new Date(), Date.from(Instant.ofEpochSecond(LocalDateTime.parse(Objects.requireNonNull(env.getProperty("auth.server.key.public.validity"))).toEpochSecond(ZoneOffset.UTC))), X500Name, keyPair.getPublic());
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                .setProvider(keyPairGenerator.getProvider())
                .build(keyPair.getPrivate());
        X509CertificateHolder holder = certificateBuilder.build(signer);

        JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
        converter.setProvider(new BouncyCastleProvider());
        return converter.getCertificate(holder);
    }
}
