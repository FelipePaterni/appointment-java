package com.paterni.appointment.domain.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.mappers.ProfessionalMapper;
import com.paterni.appointment.domain.mappers.TimeSlotMapper;
import com.paterni.appointment.domain.repositories.ProfessionalRepository;
import com.paterni.appointment.domain.services.usecases.read.SearchProfessionalAvailabiltyDaysUseCase;
import com.paterni.appointment.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;
import com.paterni.appointment.dto.ProfessionalResponse;
import com.paterni.appointment.dto.TimeSlotResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessionalService {
    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;
    @Autowired
    private SearchProfessionalAvailabiltyDaysUseCase searchProfessionalAvailabiltyDaysUseCase;

    public ProfessionalResponse getById(Long id) {
        var professional = professionalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Professional with id " + id + " not found "));
        return ProfessionalMapper.toProfessionalResponseDTO(professional);

    }

    public void getWorkdays(Long id) {

    }

    public List<Integer> getAvaliabilityeDays(Long id, Integer month, Integer year) {
        var professional = professionalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Professional with id " + id + " not found "));
        return searchProfessionalAvailabiltyDaysUseCase.executeUseCase(professional, month, year);

    }

    public List<TimeSlotResponse> getAvaliabilityeTimes(Long id, LocalDate date) {
        var professional = professionalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Professional with id " + id + " not found "));

        var timeSlots = searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional, date);

        return timeSlots.stream().map(TimeSlotMapper::toTimeSlotResponseDTO).toList();
    }

}
