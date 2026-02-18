package com.paterni.appointment.web.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paterni.appointment.domain.services.AreaService;
import com.paterni.appointment.dto.Area.AreaResponse;
import com.paterni.appointment.dto.Professional.ProfessionalResponse;

@RestController
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaResponse>> getAll() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @GetMapping("/{areaId}/professionals")
    public ResponseEntity<List<ProfessionalResponse>> getProfessionalsByAreaId(@PathVariable Integer areaId) {
        return ResponseEntity.ok(areaService.getProfessionalsByAreaId(areaId));
    }

}
