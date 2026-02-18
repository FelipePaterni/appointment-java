package com.paterni.appointment.dto.Client;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequest(
        @NotBlank(message = "Name required") String name,
        @NotBlank(message = "Phone required")
        String phone,
        @NotNull(message = "Date of birth required") 
        LocalDate dateOfBirth

) {

}
