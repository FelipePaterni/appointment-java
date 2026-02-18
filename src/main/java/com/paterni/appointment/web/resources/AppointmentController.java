package com.paterni.appointment.web.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paterni.appointment.domain.services.AppointmentService;
import com.paterni.appointment.dto.Appointment.AppointmentRequest;
import com.paterni.appointment.dto.Appointment.AppointmentResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping()
    public ResponseEntity<AppointmentResponse> save(@Validated @RequestBody AppointmentRequest request) {

        AppointmentResponse response = appointmentService.save(request);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);

    }

}