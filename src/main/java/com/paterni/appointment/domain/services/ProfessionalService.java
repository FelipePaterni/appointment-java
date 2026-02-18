package com.paterni.appointment.domain.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paterni.appointment.domain.entities.Professional;
import com.paterni.appointment.domain.mappers.ProfessionalMapper;
import com.paterni.appointment.domain.mappers.TimeSlotMapper;
import com.paterni.appointment.domain.repositories.ProfessionalRepository;
import com.paterni.appointment.domain.services.exceptions.ParameterException;
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

    @Transactional(readOnly = true)
    public ProfessionalResponse getByIdResponseProfessional(Long professionalId) {
        var professional = getProfessional(professionalId);
        return ProfessionalMapper.toProfessionalResponseDTO(professional);
    }

    @Transactional(readOnly = true)
    public void getWorkdays(Long professionalId) {

    }

    @Transactional(readOnly = true)
    public List<Integer> getAvaliabilityeDays(Long professionalId, Integer month, Integer year) {
        cheakProfessionalExistOrThrowException(professionalId);

        checkMonthIsValidOrThrowsException(month);
        checkYearIsValidOrThrowsException(year);
        checkMonthAndCurrentYearAreValidOrThrowsException(month, year);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return searchProfessionalAvailabiltyDaysUseCase.executeUseCase(professionalId, start, end);
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getAvaliabilityeTimes(Long professionalId, LocalDate date) {

        var timeSlots = searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professionalId, date);

        return timeSlots.stream().map(TimeSlotMapper::toTimeSlotResponseDTO).toList();
    }

    private Professional getProfessional(Long id) {
        return professionalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Professional with id " + id + " not found "));
    }

    private void cheakProfessionalExistOrThrowException(Long id) {
        if (!professionalRepository.existsById(id)) {
            throw new EntityNotFoundException("Professional with id " + id + " not found ");
        }
    }

    private void checkMonthAndCurrentYearAreValidOrThrowsException(int month, int year) {
        if (LocalDate.now().getYear() == year && month < LocalDate.now().getMonthValue()) {
            throw new ParameterException(
                    "Invalid month for the current year. Month must be current month or in the future.");
        }
    }

    private void checkMonthIsValidOrThrowsException(int month) {
        if (month < 1 || month > 12) {
            throw new ParameterException("Invalid month. Month must be between 1 and 12.");
        }

    }

    private void checkYearIsValidOrThrowsException(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new ParameterException("Invalid year. Year must be current year or in the future.");
        }
    }

}
