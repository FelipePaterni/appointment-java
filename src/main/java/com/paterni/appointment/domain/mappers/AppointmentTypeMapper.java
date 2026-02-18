package com.paterni.appointment.domain.mappers;

import com.paterni.appointment.domain.entities.AppointmentType;
import com.paterni.appointment.dto.AppointmentTypeResponse;

public class AppointmentTypeMapper {
    public static AppointmentTypeResponse toAppointmentTypeResponseDTO(AppointmentType appointmentType) {
        return new AppointmentTypeResponse(
                appointmentType.getId(),
                appointmentType.getType()
        );
    }
}
