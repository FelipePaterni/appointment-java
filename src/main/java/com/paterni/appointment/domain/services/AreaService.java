package com.paterni.appointment.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paterni.appointment.domain.mappers.AreaMapper;
import com.paterni.appointment.domain.mappers.ProfessionalMapper;
import com.paterni.appointment.domain.repositories.AreaRepository;
import com.paterni.appointment.dto.Area.AreaResponse;
import com.paterni.appointment.dto.Professional.ProfessionalResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Transactional(readOnly = true)
    public List<AreaResponse> getAll() {
        return areaRepository.findAll().stream().map(AreaMapper::toAreaResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProfessionalResponse> getProfessionalsByAreaId(Integer areaId) {
        if (!areaRepository.existsById(areaId)) {
            throw new EntityNotFoundException("Area not found with id: " + areaId);
        }
        var professionalsByArea = areaRepository.findActiveProfessionalsByAreaId(areaId);
        return professionalsByArea.stream().map(ProfessionalMapper::toProfessionalResponseDTO)
                .toList();
    }

}
