package me.levani.authorizationserver.service;

import lombok.RequiredArgsConstructor;
import me.levani.authorizationserver.model.domain.KeyStoreModel;
import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.response.PrivateKeyEntryResponse;
import me.levani.authorizationserver.repository.RealmRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeyStoreService {

    private final RealmRepository realmRepository;
    private final Environment env;
    public PrivateKeyEntryResponse getKeys(Long realmId) throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException, IOException {
        Realm realm = realmRepository.findById(realmId)
                .orElseThrow();
        return getPrivateKeyEntryResponse(realm);

    }

    public PrivateKeyEntryResponse getKeys(String realmName) throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException, IOException {
        Realm realm = realmRepository.findByRealmNameAndEntityStatus(realmName, EntityStatus.ACTIVE)
                .orElseThrow();
        return getPrivateKeyEntryResponse(realm);

    }

    private PrivateKeyEntryResponse getPrivateKeyEntryResponse(Realm realm) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        KeyStoreModel keyStoreModel = realm.getKeyStoreModel();
        KeyStore jks = KeyStore.getInstance("JKS");
        jks.load(new ByteArrayInputStream(keyStoreModel.getKeyStore()), Objects.requireNonNull(env.getProperty("auth.server.keystore.password")).toCharArray());
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(Objects.requireNonNull(env.getProperty("auth.server.key.password")).toCharArray());
        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) jks.getEntry(keyStoreModel.getKid(), protectionParameter);
        return new PrivateKeyEntryResponse(entry, keyStoreModel.getKid());
    }
}
