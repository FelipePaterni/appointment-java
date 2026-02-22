package com.paterni.appointment.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paterni.appointment.domain.repositories.AppointmentRepository;

@Service
public class SearchProfessionalAvailabiltyDaysUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional(readOnly = true)
    public List<Integer> executeUseCase(long professionalId, LocalDate start, LocalDate end) {
        return appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);

    }

}
