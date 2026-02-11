package com.paterni.appointment.dto;

import java.time.LocalDate;

public record ClientResponse(
        long id,
        String name,
        String phone,
        LocalDate dateOfBirth) {

}
