package com.paterni.appointment.dto.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.paterni.appointment.domain.entities.AppointmentStatus;
import com.paterni.appointment.dto.IntegerDTO;
import com.paterni.appointment.dto.LongDTO;

public record AppointmentResponse(
        long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String comments,
        AppointmentStatus status,
        IntegerDTO type,
        IntegerDTO area,
        LongDTO professional,
        LongDTO client) {

}
