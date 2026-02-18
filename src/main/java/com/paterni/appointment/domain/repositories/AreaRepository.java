package com.paterni.appointment.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paterni.appointment.domain.entities.Area;
import com.paterni.appointment.domain.entities.Professional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

    @Query("SELECT p FROM Professional p JOIN p.areas a WHERE a.id = :areaId AND p.active = true")
    List<Professional> findActiveProfessionalsByAreaId(Integer areaId);

}
