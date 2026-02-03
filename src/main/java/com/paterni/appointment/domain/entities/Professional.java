package com.paterni.appointment.domain.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROFESSIONAL")
@PrimaryKeyJoinColumn(name = "Person_Id")
public class Professional extends Person {
    private Boolean active;

    @ManyToMany
    @JoinTable(name = "TBL_PROFESSIONAL_AREA", joinColumns = @JoinColumn(name = "professional_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
    private Set<Area> areas;

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
