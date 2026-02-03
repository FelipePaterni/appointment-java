package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paterni.appointment.domain.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
