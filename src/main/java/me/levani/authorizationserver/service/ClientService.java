package me.levani.authorizationserver.service;

import me.levani.authorizationserver.model.domain.Client;
import me.levani.authorizationserver.model.enums.EntityStatus;
import me.levani.authorizationserver.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Client findById(Long id){
        return clientRepository.findByIdAndStatus(id, EntityStatus.ACTIVE)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
