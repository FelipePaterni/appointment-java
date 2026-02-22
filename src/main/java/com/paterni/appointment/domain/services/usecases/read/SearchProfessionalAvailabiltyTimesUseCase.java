package com.paterni.appointment.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paterni.appointment.domain.models.TimeSlot;
import com.paterni.appointment.domain.repositories.AppointmentRepository;


@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional(readOnly = true)
    public List<TimeSlot> executeUseCase(long professionalId, LocalDate date) {
        return appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
    }

}