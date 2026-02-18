package com.paterni.appointment.web.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paterni.appointment.domain.services.ProfessionalService;
import com.paterni.appointment.dto.TimeSlotResponse;
import com.paterni.appointment.dto.Professional.ProfessionalResponse;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("professionals")
@Validated
public class ProfessionalController {
    @Autowired
    ProfessionalService professionalService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(professionalService.getByIdResponseProfessional(id));
    }

    @GetMapping("{id}/workdays")
    public ResponseEntity<String> getWorkdays(@PathVariable Long id) {
        professionalService.getWorkdays(id);
        return ResponseEntity.ok("Workdays for professional id: " + id);
    }

    @GetMapping("{id}/avaliability-days")
    public ResponseEntity<List<Integer>> getAvaliabilityeDays(
            @PathVariable Long id,

            @RequestParam(required = false) @NotNull(message = "Month is required") @Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "Month must be a number between 1 and 12")

            String month,

            @RequestParam(required = false) @NotNull(message = "Year is required") @Min(value = 1900, message = "Year must be greater than 1900") @Pattern(regexp = "^[0-9]{4}$", message = "Year must be a 4-digit number") String year

    ) {
        List<Integer> availableDays = professionalService.getAvaliabilityeDays(id, Integer.valueOf(month),
                Integer.valueOf(year));

        return ResponseEntity.ok(availableDays);
    }

    @GetMapping("{id}/avaliability-times")
    public ResponseEntity<List<TimeSlotResponse>> getAvaliabilityeTimes(@PathVariable Long id,
            @RequestParam(required = false) @NotNull(message = "Date is required") @FutureOrPresent(message = "Date must be present or future") LocalDate date) {
        List<TimeSlotResponse> timeSlots = professionalService.getAvaliabilityeTimes(id, date);

        return ResponseEntity.ok(timeSlots);
    }

}
