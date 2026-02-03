package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paterni.appointment.domain.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
