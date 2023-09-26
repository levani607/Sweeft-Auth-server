package com.example.authorizationserver.facade;

import com.example.authorizationserver.model.domain.RealmUser;
import com.example.authorizationserver.model.request.UserRequest;
import com.example.authorizationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService userService;

    public void saveUser(UserRequest userRequest){
        RealmUser realmUser = new RealmUser();
        //Todo create user
    }
}
