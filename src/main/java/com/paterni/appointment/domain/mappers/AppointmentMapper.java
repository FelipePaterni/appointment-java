package com.paterni.appointment.domain.mappers;

import org.springframework.beans.BeanUtils;

import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.entities.AppointmentType;
import com.paterni.appointment.domain.entities.Area;
import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.domain.entities.Professional;
import com.paterni.appointment.dto.IntegerDTO;
import com.paterni.appointment.dto.LongDTO;
import com.paterni.appointment.dto.Appointment.AppointmentRequest;
import com.paterni.appointment.dto.Appointment.AppointmentResponse;

public class AppointmentMapper {

    public static AppointmentResponse toAppointmentResponseDTO(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getComments(),
                appointment.getStatus(),
                new IntegerDTO(appointment.getAppointmentType().getId()),
                new IntegerDTO(appointment.getArea().getId()),
                new LongDTO(appointment.getProfessional().getId()),
                new LongDTO(appointment.getClient().getId()));
    }

    public static Appointment fromAppointmentRequestDTO(AppointmentRequest request) {
        Appointment appointment = new Appointment();

        BeanUtils.copyProperties(request, appointment);
        appointment.setArea(new Area(request.area().id()));
        appointment.setAppointmentType(new AppointmentType(request.type().id()));
        appointment.setProfessional(new Professional(request.professional().id()));
        appointment.setClient(new Client(request.client().id()));

        return appointment;
    }

}
