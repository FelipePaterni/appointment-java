package com.paterni.appointment.dto;

//TODO: add validation annotations
public record ProfessionalRequest(
                String name,
                String phone,
                boolean active) {

}
