package me.levani.authorizationserver.facade;

import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.request.RealmRequest;
import me.levani.authorizationserver.service.RealmService;
import me.levani.authorizationserver.service.RsaRealmKeyGenerator;
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
