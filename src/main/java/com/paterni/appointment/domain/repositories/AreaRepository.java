package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paterni.appointment.domain.entities.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

}
