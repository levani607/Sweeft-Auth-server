package com.example.authorizationserver.service;

import com.example.authorizationserver.model.domain.RealmUser;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public boolean existByUsername(String username, Long realmId) {
        return userRepository.existsByUsernameAndRealmId(username, realmId, EntityStatus.ACTIVE);
    }

    public RealmUser save(RealmUser realmUser){
        return userRepository.save(realmUser);
    }
}
