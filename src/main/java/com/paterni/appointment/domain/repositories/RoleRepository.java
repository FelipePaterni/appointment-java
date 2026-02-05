package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paterni.appointment.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
