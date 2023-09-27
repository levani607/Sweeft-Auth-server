package me.levani.authorizationserver.facade;

import me.levani.authorizationserver.model.domain.Client;
import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.request.ClientRequest;
import me.levani.authorizationserver.service.ClientService;
import me.levani.authorizationserver.service.RealmService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientFacade {

    private final ClientService clientService;
    private final RealmService realmService;

    public void create(ClientRequest clientRequest) {
        Realm realm = realmService.findById(clientRequest.getRealmId());
        Client client = new Client();
        client.setEntityStatus(EntityStatus.ACTIVE);
        client.setClientSecret(RandomStringUtils.randomAlphanumeric(128));

        client.setRealm(realm);
        client.setClientId(clientRequest.getClientName());
        Client save = clientService.save(client);

    }


}
