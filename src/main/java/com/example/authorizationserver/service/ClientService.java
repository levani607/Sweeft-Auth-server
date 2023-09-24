package com.example.authorizationserver.service;

import com.example.authorizationserver.model.domain.Client;
import com.example.authorizationserver.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public Client save(Client client){
        return clientRepository.save(client);
    }
}
