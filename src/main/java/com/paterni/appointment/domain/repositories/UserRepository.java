package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paterni.appointment.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
