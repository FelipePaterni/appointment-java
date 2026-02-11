package com.paterni.appointment.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.mappers.AppointmentMapper;
import com.paterni.appointment.domain.repositories.AppointmentRepository;
import com.paterni.appointment.domain.services.usecases.write.CreateAppointmentUseCase;
import com.paterni.appointment.dto.AppointmentRequest;
import com.paterni.appointment.dto.AppointmentResponse;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    CreateAppointmentUseCase createAppointmentUseCase;

    public AppointmentResponse save(AppointmentRequest request) {

        Appointment appointment = createAppointmentUseCase.executeUserCase(request);
        return AppointmentMapper.toAppointmentResponseDTO(appointment);
    }
}
