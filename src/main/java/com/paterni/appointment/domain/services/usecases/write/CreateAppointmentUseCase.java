package com.paterni.appointment.domain.services.usecases.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.mappers.AppointmentMapper;
import com.paterni.appointment.domain.repositories.AppointmentRepository;
import com.paterni.appointment.dto.AppointmentRequest;

@Service
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment executeUserCase(AppointmentRequest request) {
        return this.appointmentRepository.save(AppointmentMapper.fromAppointmentRequestDTO(request));

    }
}
