package me.levani.authorizationserver.facade;

import me.levani.authorizationserver.model.domain.RealmUser;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.model.request.UserRequest;
import me.levani.authorizationserver.service.RealmService;
import me.levani.authorizationserver.service.UserService;
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
