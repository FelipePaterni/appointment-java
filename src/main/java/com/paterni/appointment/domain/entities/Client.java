package com.paterni.appointment.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CLIENT")
@PrimaryKeyJoinColumn(name = "Person_Id")
public class Client extends Person {
    private LocalDate birthDate;

    public Client() {
        super();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
