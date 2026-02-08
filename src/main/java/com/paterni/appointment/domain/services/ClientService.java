package com.paterni.appointment.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.domain.mappers.ClientMapper;
import com.paterni.appointment.domain.repositories.ClientRepository;
import com.paterni.appointment.dto.ClientResponse;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientResponse> findByNameContainingIgnoreCase(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size); // Exemplo de paginação: primeira página com 10 itens
        Page<Client> pageClient = clientRepository.findByNameContainingIgnoreCase(name, pageRequest);
               
        return pageClient.map(c -> ClientMapper.toClientResponseDTO(c));
    }
}
