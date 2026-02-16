package com.paterni.appointment.domain.services.usecases.read;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.entities.AppointmentStatus;
import com.paterni.appointment.domain.entities.Professional;
import com.paterni.appointment.domain.entities.WorkScheduleItem;
import com.paterni.appointment.domain.models.TimeSlot;
import com.paterni.appointment.domain.repositories.AppointmentRepository;
import com.paterni.appointment.domain.repositories.WorkScheduleItemRepository;

@Service
public class SearchProfessionalAvailabiltyTimesUseCase {

    @Autowired
    private WorkScheduleItemRepository workScheduleItemRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<TimeSlot> executeUseCase(Professional professional, LocalDate date) {

        var timeSlots = new ArrayList<TimeSlot>();

        var workScheduleItems = getWorkScheduleItem(professional, date);
        var appointments = getAppointments(professional, date);

        for (WorkScheduleItem item : workScheduleItems) {
            timeSlots.addAll(calculateTimeSlots(item, appointments, date));
        }

        return timeSlots;
    }

    private List<TimeSlot> calculateTimeSlots(WorkScheduleItem item, List<Appointment> appointments, LocalDate date) {
        var startTime = item.getStartTime();
        var slotSize = item.getSlotSize();
        var slots = item.getSlots();
        var timeSlots = new ArrayList<TimeSlot>();

        for (int i = 0; i < slots; i++) {
            var start = startTime.plusMinutes(i * slotSize);
            var end = start.plusMinutes(slotSize);

            boolean available = isTimeSlotAvailable(start, end, appointments);
            boolean nowOrFuture = isStartTimeValidIfDateIsToday(start, date);

            timeSlots.add(new TimeSlot(start, end, available && nowOrFuture));
        }
        return timeSlots;
    }

    private boolean isStartTimeValidIfDateIsToday(LocalTime start, LocalDate date) {
        return date.isAfter(LocalDate.now())
                || (date.equals(LocalDate.now()) && start.isAfter(LocalTime.now()));
    }

    private boolean isTimeSlotAvailable(LocalTime start, LocalTime end, List<Appointment> appointments) {
        return appointments
                .stream()
                .noneMatch(a -> //
                (//
                a.getStartTime().isBefore(end) && //
                        a.getEndTime().isAfter(start) //
                ) && ( //
                a.getStatus().equals(AppointmentStatus.OPEN) || //
                        a.getStatus().equals(AppointmentStatus.PRESENT)//
                ));
    }

    private List<Appointment> getAppointments(Professional professional, LocalDate date) {
        return this.appointmentRepository.findByProfessionalIdAndDate(professional.getId(), date);
    }

    private List<WorkScheduleItem> getWorkScheduleItem(Professional professional, LocalDate date) {
        return this.workScheduleItemRepository
                .findByProfessionalAndDayOfWeekOrderByStartTime(professional, date.getDayOfWeek());
    }

}
