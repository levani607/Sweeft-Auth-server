package com.example.authorizationserver.facade;

import com.example.authorizationserver.model.domain.Realm;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.request.RealmRequest;
import com.example.authorizationserver.service.RealmService;
import com.example.authorizationserver.service.RsaRealmKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RealmFacade {

    private final RealmService realmService;
    private final RsaRealmKeyGenerator rsaRealmKeyGenerator;


    public void saveRealm(RealmRequest realmRequest) {
        Realm realm = new Realm();
        realm.setRealmName(realmRequest.getRealmName());
        realm.setEntityStatus(EntityStatus.ACTIVE);
        rsaRealmKeyGenerator.createKey(realm);
        realmService.save(realm);
    }


}
