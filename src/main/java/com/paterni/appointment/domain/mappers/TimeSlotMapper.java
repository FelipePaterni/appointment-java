package com.paterni.appointment.domain.mappers;

import com.paterni.appointment.domain.models.TimeSlot;
import com.paterni.appointment.dto.TimeSlotResponse;

public class TimeSlotMapper {

    public static TimeSlotResponse toTimeSlotResponseDTO(TimeSlot timeSlot) {
        return new TimeSlotResponse(
                timeSlot.getStartTime().toLocalTime(),
                timeSlot.getEndTime().toLocalTime(),
                timeSlot.isAvailable());
    }

    /*
     * public static TimeSlot fromTimeSlotRequestDTO(TimeSlotRequest
     * professionalRequest) {
     * return new TimeSlot(
     * professionalRequest.name(),
     * professionalRequest.phone(),
     * professionalRequest.active());
     * }
     */
}