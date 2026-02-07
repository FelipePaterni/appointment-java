package com.paterni.appointment.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.mappers.ClientMapper;
import com.paterni.appointment.domain.repositories.ClientRepository;
import com.paterni.appointment.dto.ClientResponse;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientResponse> getAll(){
        return clientRepository.findByNameContainingIgnoreCase("MA").stream().map(ClientMapper::toClientResponseDTO).collect(Collectors.toList());
    }
}
