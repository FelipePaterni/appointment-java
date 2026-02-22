package com.paterni.appointment.domain.convertes;

import java.time.DayOfWeek;

import jakarta.persistence.AttributeConverter;

public interface DayOfWeekInterface extends AttributeConverter<DayOfWeek, Integer> {

}
