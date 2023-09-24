package com.example.authorizationserver.facade;

import com.example.authorizationserver.model.domain.Client;
import com.example.authorizationserver.model.domain.Realm;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.request.ClientRequest;
import com.example.authorizationserver.service.ClientService;
import com.example.authorizationserver.service.RealmService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
        client.setClientSecret(RandomStringUtils.randomAlphanumeric(256));

        client.setRealm(realm);
        client.setClientId(client.getClientId());
        Client save = clientService.save(client);

    }


}
