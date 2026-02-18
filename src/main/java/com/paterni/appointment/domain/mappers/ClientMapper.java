package com.paterni.appointment.domain.mappers;

import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.dto.Client.ClientRequest;
import com.paterni.appointment.dto.Client.ClientResponse;

public class ClientMapper {
    public static ClientResponse toClientResponseDTO(Client client) {

        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getDateOfBirth());
    }

    public static Client fromClientRequestDTO(ClientRequest clientRequest) {
        return new Client(clientRequest.name(), clientRequest.phone(), clientRequest.dateOfBirth());
    }
}
