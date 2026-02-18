package com.paterni.appointment.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.models.TimeSlot;
import com.paterni.appointment.domain.repositories.AppointmentRepository;

@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<TimeSlot> executeUseCase(long professionalId, LocalDate date) {
        return appointmentRepository.getAvailableTimeFromProfessional(professionalId, date);
    }

}