package com.example.authorizationserver.model.core;

import com.example.authorizationserver.model.domain.Realm;

public interface RealmKeyGenerator {
    void createKey(Realm realm);
}
