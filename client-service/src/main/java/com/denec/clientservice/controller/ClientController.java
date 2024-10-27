package com.denec.clientservice.controller;

import com.denec.clientservice.model.dto.ClientDto;
import com.denec.clientservice.model.request.ClientRegisterRequest;
import com.denec.clientservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/register")
    public ClientDto registerClient(@RequestBody ClientRegisterRequest request) {
        return clientService.registerClient(request);
    }
}