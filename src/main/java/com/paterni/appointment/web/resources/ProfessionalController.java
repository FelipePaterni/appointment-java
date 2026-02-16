package com.paterni.appointment.web.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paterni.appointment.domain.services.ProfessionalService;
import com.paterni.appointment.dto.ProfessionalResponse;
import com.paterni.appointment.dto.TimeSlotResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("professionals")
public class ProfessionalController {
    @Autowired
    ProfessionalService professionalService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(professionalService.getById(id));
    }

    @GetMapping("{id}/workdays")
    public ResponseEntity<String> getWorkdays(@PathVariable Long id) {
        professionalService.getWorkdays(id);
        return ResponseEntity.ok("Workdays for professional id: " + id);
    }

    @GetMapping("{id}/avaliability-days")
    public ResponseEntity<String> getAvaliabilityeDays(@PathVariable Long id) {
        professionalService.getAvaliabilityeDays(id);
        return ResponseEntity.ok("Available days for professional id: " + id);
    }

    @GetMapping("{id}/avaliability-times")
    public ResponseEntity<List<TimeSlotResponse>> getAvaliabilityeTimes(@PathVariable Long id,
            @RequestParam LocalDate date) {
        List<TimeSlotResponse> timeSlots = professionalService.getAvaliabilityeTimes(id, date);

        return ResponseEntity.ok(timeSlots);
    }

}
