package com.paterni.appointment.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.mappers.AppointmentMapper;
import com.paterni.appointment.domain.mappers.AppointmentTypeMapper;
import com.paterni.appointment.domain.repositories.AppointmentTypeRepository;
import com.paterni.appointment.domain.services.usecases.write.CreateAppointmentUseCase;
import com.paterni.appointment.dto.AppointmentTypeResponse;
import com.paterni.appointment.dto.Appointment.AppointmentRequest;
import com.paterni.appointment.dto.Appointment.AppointmentResponse;

@Service
public class AppointmentService {

    @Autowired
    private CreateAppointmentUseCase createAppointmentUseCase;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Transactional
    public AppointmentResponse save(AppointmentRequest request) {

        Appointment appointment = createAppointmentUseCase
                .executeUserCase(AppointmentMapper.fromAppointmentRequestDTO(request));
        return AppointmentMapper.toAppointmentResponseDTO(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentTypeResponse> getAllTypes() {
        return appointmentTypeRepository.findAll()
                .stream()
                .map(AppointmentTypeMapper::toAppointmentTypeResponseDTO)
                .collect(Collectors.toList());
    }
}
