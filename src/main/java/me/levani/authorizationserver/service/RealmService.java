package me.levani.authorizationserver.service;

import me.levani.authorizationserver.model.domain.Realm;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.repository.RealmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class RealmService {

    private final RealmRepository realmRepository;

    public boolean existsWithName(String name){
        return  realmRepository.existsByRealmNameAndEntityStatus(name, EntityStatus.ACTIVE);
    }

    public Realm findByName(String name) {
        return realmRepository.findByRealmNameAndEntityStatus(name, EntityStatus.ACTIVE)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public Realm findById(Long id){
        return realmRepository.findByIdAndEntityStatus(id,EntityStatus.ACTIVE)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public Realm save(Realm realm){
        return realmRepository.save(realm);
    }

    public Page<Realm> listRealms(Pageable pageable){
        return realmRepository.findAll(pageable);
    }
}
