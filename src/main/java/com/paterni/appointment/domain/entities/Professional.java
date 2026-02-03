package com.paterni.appointment.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROFESSIONAL")
@PrimaryKeyJoinColumn(name = "Person_Id")
public class Professional extends Person {
    private Boolean active;

    public Professional() {
        super();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
