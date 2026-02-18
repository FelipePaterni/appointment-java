package com.paterni.appointment.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.domain.mappers.ClientMapper;
import com.paterni.appointment.domain.repositories.ClientRepository;
import com.paterni.appointment.domain.services.exceptions.DatabaseException;
import com.paterni.appointment.dto.Client.ClientRequest;
import com.paterni.appointment.dto.Client.ClientResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientResponse> findByNameContainingIgnoreCase(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size); // Exemplo de paginação: primeira página com 10 itens
        Page<Client> pageClient = clientRepository.findByNameContainingIgnoreCase(name, pageRequest);

        return pageClient.map(c -> ClientMapper.toClientResponseDTO(c));
    }

    @Transactional(readOnly = true)
    public ClientResponse getById(long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Client with id " + id + " not found "));
        return ClientMapper.toClientResponseDTO(client);
    }

    @Transactional
    public ClientResponse save(ClientRequest clientRequest) {
        Client client = clientRepository.save(ClientMapper.fromClientRequestDTO(clientRequest));
        return ClientMapper.toClientResponseDTO(client);

    }

    @Transactional
    public void update(long id, ClientRequest request) {
        try {
            Client client = clientRepository.getReferenceById(id);

            client.setName(request.name());
            client.setPhone(request.phone());
            client.setDateOfBirth(request.dateOfBirth());

            clientRepository.save(client);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Client with id " + id + " not found");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            if (clientRepository.existsById(id)) {
                clientRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("Client with id " + id + " not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Conflito ao remover o cliente");
        }
    }
}
