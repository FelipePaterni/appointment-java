package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paterni.appointment.domain.entities.WorkScheduleItem;

@Repository
public interface WorkScheduleItemRepository extends JpaRepository<WorkScheduleItem, Long> {
}