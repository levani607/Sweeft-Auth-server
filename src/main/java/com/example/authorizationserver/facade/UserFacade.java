package com.example.authorizationserver.facade;

import com.example.authorizationserver.model.domain.RealmUser;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.request.UserRequest;
import com.example.authorizationserver.service.RealmService;
import com.example.authorizationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final RealmService realmService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserRequest request) {
        RealmUser realmUser = new RealmUser();
        realmUser.setRealm(realmService.findById(request.getRealmId()));
        realmUser.setPassword(passwordEncoder.encode(request.getPassword()));
        realmUser.setFirstname(request.getFirstname());
        realmUser.setLastname(request.getLastname());
        realmUser.setMiddleName(request.getMiddleName());
        realmUser.setUsername(request.getUsername());
        realmUser.setEntityStatus(EntityStatus.ACTIVE);
        userService.save(realmUser);
    }
}
