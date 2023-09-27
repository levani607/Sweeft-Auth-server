package me.levani.authorizationserver.mappers;

import me.levani.authorizationserver.model.domain.RealmUser;
import me.levani.authorizationserver.model.response.PayloadResponse;

public class UserMapper {


    public static void mapOpenIdInfo(RealmUser user, PayloadResponse response){
        response.setName(user.getUsername());
        response.setLastname(user.getLastname());
        response.setMiddleName(user.getMiddleName());
        response.setName(user.getFirstname());
    }

    public static void mapBasicInfo(RealmUser user, PayloadResponse response) {
        response.setUsername(user.getUsername());
        response.setSubject(user.getId().toString());
    }

}
