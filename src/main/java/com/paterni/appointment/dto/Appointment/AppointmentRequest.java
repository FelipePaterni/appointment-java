package com.paterni.appointment.dto.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.paterni.appointment.dto.IntegerDTO;
import com.paterni.appointment.dto.LongDTO;

public record AppointmentRequest(
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String comments,
        IntegerDTO type,
        IntegerDTO area,
        LongDTO professional,
        LongDTO client) {

}
