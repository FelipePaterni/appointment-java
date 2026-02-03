package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paterni.appointment.domain.entities.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
