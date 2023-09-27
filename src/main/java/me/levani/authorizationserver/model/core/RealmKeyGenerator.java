package me.levani.authorizationserver.model.core;

import me.levani.authorizationserver.model.domain.Realm;

public interface RealmKeyGenerator {
    void createKey(Realm realm);
}
