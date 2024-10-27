package com.denec.clientservice.service;

import com.denec.clientservice.mapper.ClientMapper;
import com.denec.clientservice.model.Client;
import com.denec.clientservice.model.dto.ClientDto;
import com.denec.clientservice.model.request.ClientRegisterRequest;
import com.denec.clientservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDto registerClient(ClientRegisterRequest request) {
        Client client = clientMapper.toEntity(request);
        return clientMapper.toDto(clientRepository.save(client));
    }
}