package com.paterni.appointment.domain.mappers;

import com.paterni.appointment.domain.entities.Professional;
import com.paterni.appointment.dto.Professional.ProfessionalRequest;
import com.paterni.appointment.dto.Professional.ProfessionalResponse;

public class ProfessionalMapper {

    public static ProfessionalResponse toProfessionalResponseDTO(Professional professional) {
        return new ProfessionalResponse(
                professional.getId(),
                professional.getName(),
                professional.getPhone(),
                professional.isActive());
    }

    public static Professional fromProfessionalRequestDTO(ProfessionalRequest professionalRequest) {
        return new Professional(
                professionalRequest.name(),
                professionalRequest.phone(),
                professionalRequest.active());
    }

}
