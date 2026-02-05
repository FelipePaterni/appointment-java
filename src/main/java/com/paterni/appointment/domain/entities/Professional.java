package com.paterni.appointment.domain.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROFESSIONAL")
@PrimaryKeyJoinColumn(name = "Person_Id")
public class Professional extends Person {
    private Boolean active;

    @ManyToMany
    @JoinTable(name = "TBL_AREA_PROFESSIONAL", joinColumns = @JoinColumn(name = "professional_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
    private Set<Area> areas;

    @OneToMany
    @JoinColumn(name = "professional_id")
    private List<WorkScheduleItem> workScheduleItems;

    @OneToMany(mappedBy = "professional")
    private List<Appointment> appointments = new ArrayList<>();

    public Professional() {
        super();
    }

    public Boolean getActive() {
        return active;
    }

    public void addWorkScheduleItem(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Integer slots,
            Integer slotSize) {

        WorkScheduleItem wsi = new WorkScheduleItem(dayOfWeek, startTime, endTime, slots, slotSize);
        this.workScheduleItems.add(wsi);
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    public List<WorkScheduleItem> getWorkScheduleItems() {
        return workScheduleItems;
    }

    public void setWorkScheduleItems(List<WorkScheduleItem> workScheduleItems) {
        this.workScheduleItems = workScheduleItems;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Professional [active=" + active + super.toString() + "]";
    }

}
