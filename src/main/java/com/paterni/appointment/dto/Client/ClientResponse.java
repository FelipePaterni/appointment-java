package com.paterni.appointment.dto.Client;

import java.time.LocalDate;

public record ClientResponse(
        long id,
        String name,
        String phone,
        LocalDate dateOfBirth) {

}
