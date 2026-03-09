package com.paterni.appointment.unit.domain.repositories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.domain.models.TimeSlot;
import com.paterni.appointment.domain.repositories.AppointmentRepository;
import com.paterni.appointment.unit.factory.TimeSlotFactory;

@DataJpaTest
@EnabledIf(expression = "#{environment.acceptsProfiles('test')}", reason = "Only runs in test profile", loadContext = true)
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void existsOpenOrPresentAppointmentsForClientShouldReturnTrue() {
        Client client3 = new Client(3L);
        LocalDate date = LocalDate.of(2035, 4, 10);
        LocalTime startTime = LocalTime.of(8, 0, 0);
        LocalTime endTime = LocalTime.of(8, 30, 0);

        var found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);

        assertTrue(found);

        startTime = LocalTime.of(8, 1);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(8, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(9, 2);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(9, 0);
        endTime = LocalTime.of(11, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(6, 0);
        endTime = LocalTime.of(12, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertTrue(found);

        Client client4 = new Client(4L);
        startTime = LocalTime.of(6, 0);
        endTime = LocalTime.of(12, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(9, 30);
        endTime = LocalTime.of(10, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertTrue(found);

        startTime = LocalTime.of(9, 15);
        endTime = LocalTime.of(10, 15);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertTrue(found);
    }

    @Test
    void existsOpenOrPresentAppointmentsForClientShouldReturnFalse() {
        Client client3 = new Client(3L);
        LocalDate date = LocalDate.of(2035, 4, 10);
        LocalTime startTime = LocalTime.of(7, 30, 0);
        LocalTime endTime = LocalTime.of(8, 0, 0);

        var found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);

        assertFalse(found);

        startTime = LocalTime.of(9, 30);
        endTime = LocalTime.of(10, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(7, 59);
        endTime = LocalTime.of(8, 0);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(9, 30);
        endTime = LocalTime.of(9, 31);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client3, date, startTime, endTime);
        assertFalse(found);

        Client client4 = new Client(4L);
        startTime = LocalTime.of(8, 0);
        endTime = LocalTime.of(8, 30);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(8, 0);
        endTime = LocalTime.of(9, 30);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);

        startTime = LocalTime.of(7, 0);
        endTime = LocalTime.of(9, 30);
        found = appointmentRepository.existsOpenOrPresentAppointmentsForClient(client4, date, startTime, endTime);
        assertFalse(found);
    }

    @Test
    void getAvailableDaysFromProfessionalShouldReturnEmptyList() {
        Long professionalId = 6L;
        LocalDate start = LocalDate.of(2035, 4, 4);
        LocalDate end = LocalDate.of(2035, 4, 5);

        var foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);

        assertTrue(foundDays.isEmpty());

        start = LocalDate.of(2035, 4, 7);
        end = LocalDate.of(2035, 4, 7);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        assertTrue(foundDays.isEmpty());

        start = LocalDate.of(2035, 4, 11);
        end = LocalDate.of(2035, 4, 12);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        assertTrue(foundDays.isEmpty());

        professionalId = 7L;
        start = LocalDate.of(2035, 4, 6);
        end = LocalDate.of(2035, 4, 8);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);

        assertTrue(foundDays.isEmpty());

        start = LocalDate.of(2035, 4, 13);
        end = LocalDate.of(2035, 4, 14);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        assertTrue(foundDays.isEmpty());

        start = LocalDate.of(2035, 4, 20);
        end = LocalDate.of(2035, 4, 20);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        assertTrue(foundDays.isEmpty());
    }

    @Test
    void getAvailableDaysFromProfessionalShouldReturnListOfInteger() {

        Long professionalId = 6L;
        var start = LocalDate.of(2035, 4, 1);
        var end = LocalDate.of(2035, 4, 30);
        var foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        var expectDays = List.of(2, 3, 6, 9, 10, 13, 16, 17, 20, 23, 24, 27, 30);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2035, 4, 1);
        end = LocalDate.of(2035, 4, 8);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(2, 3, 6);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2035, 4, 3);
        end = LocalDate.of(2035, 4, 15);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(3, 6, 9, 10, 13);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2035, 4, 3);
        end = LocalDate.of(2035, 4, 4);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(3);
        assertIterableEquals(expectDays, foundDays);

        professionalId = 7L;
        start = LocalDate.of(2035, 4, 1);
        end = LocalDate.of(2035, 4, 30);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(2, 3, 9, 10, 16, 17, 23, 24, 30);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2035, 4, 1);
        end = LocalDate.of(2035, 4, 8);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(2, 3);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2035, 4, 3);
        end = LocalDate.of(2035, 4, 15);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(3, 9, 10);
        assertIterableEquals(expectDays, foundDays);

        start = LocalDate.of(2035, 4, 3);
        end = LocalDate.of(2035, 4, 4);
        foundDays = appointmentRepository.getAvailableDaysFromProfessional(professionalId, start, end);
        expectDays = List.of(3);
        assertIterableEquals(expectDays, foundDays);

    }

    @Test
    void getAvailableTimesFromProfessionalShouldReturnEmptyList() {
        Long professionalId = 6L;
        LocalDate date = LocalDate.of(2035, 4, 5);
        List<TimeSlot> foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        assertTrue(foundTimes.isEmpty());

        date = LocalDate.of(2024, 4, 6);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        assertTrue(foundTimes.isEmpty());

        professionalId = 7L;
        date = LocalDate.of(2024, 4, 6);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        assertTrue(foundTimes.isEmpty());

        date = LocalDate.of(2024, 4, 7);
        foundTimes = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        assertTrue(foundTimes.isEmpty());
    }

    @Test
    void getAvailableTimesFromProfessionalShouldReturnListOfTimeSlots() {

        // Professional 6 trabalha:
        // Monday (2): 08:00-10:00 (4 slots)
        // Tuesday (3): 08:00-12:00 (8 slots) + 14:00-18:00 (8 slots)
        // Friday (6): 08:00-12:00 (8 slots)

        Long professionalId = 6L;

        // 2035-04-02 é Monday - 4 slots disponíveis
        LocalDate date = LocalDate.of(2035, 4, 2);
        List<TimeSlot> foundTimeSlots = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        List<TimeSlot> expTimeSlots = List.of(
                TimeSlotFactory.createTimeSlot("08:00:00-03:00", "08:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("08:30:00-03:00", "09:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("09:00:00-03:00", "09:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("09:30:00-03:00", "10:00:00-03:00", true));
        checkTimeSlotsLists(expTimeSlots, foundTimeSlots);

        // 2035-04-10 é Wednesday com appointments - 3 ocupados (client 3) + 1
        // disponível
        date = LocalDate.of(2035, 4, 10);
        foundTimeSlots = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expTimeSlots = List.of(
                TimeSlotFactory.createTimeSlot("08:00:00-03:00", "08:30:00-03:00", false),
                TimeSlotFactory.createTimeSlot("08:30:00-03:00", "09:00:00-03:00", false),
                TimeSlotFactory.createTimeSlot("09:00:00-03:00", "09:30:00-03:00", false),
                TimeSlotFactory.createTimeSlot("09:30:00-03:00", "10:00:00-03:00", false),
                TimeSlotFactory.createTimeSlot("10:00:00-03:00", "10:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("10:30:00-03:00", "11:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("11:00:00-03:00", "11:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("11:30:00-03:00", "12:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("14:00:00-03:00", "14:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("14:30:00-03:00", "15:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("15:00:00-03:00", "15:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("15:30:00-03:00", "16:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("16:00:00-03:00", "16:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("16:30:00-03:00", "17:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("17:00:00-03:00", "17:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("17:30:00-03:00", "18:00:00-03:00", true));
        checkTimeSlotsLists(expTimeSlots, foundTimeSlots);

        // 2035-04-03 é Wednesday - 16 slots disponíveis (manhã + tarde)
        date = LocalDate.of(2035, 4, 3);
        foundTimeSlots = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expTimeSlots = List.of(
                TimeSlotFactory.createTimeSlot("08:00:00-03:00", "08:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("08:30:00-03:00", "09:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("09:00:00-03:00", "09:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("09:30:00-03:00", "10:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("10:00:00-03:00", "10:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("10:30:00-03:00", "11:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("11:00:00-03:00", "11:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("11:30:00-03:00", "12:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("14:00:00-03:00", "14:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("14:30:00-03:00", "15:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("15:00:00-03:00", "15:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("15:30:00-03:00", "16:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("16:00:00-03:00", "16:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("16:30:00-03:00", "17:00:00-03:00", true),
                TimeSlotFactory.createTimeSlot("17:00:00-03:00", "17:30:00-03:00", true),
                TimeSlotFactory.createTimeSlot("17:30:00-03:00", "18:00:00-03:00", true));
        checkTimeSlotsLists(expTimeSlots, foundTimeSlots);

        // 2035-04-04 é Thursday - Professional 6 não trabalha quinta
        date = LocalDate.of(2035, 4, 4);
        foundTimeSlots = appointmentRepository.getAvailableTimesFromProfessional(professionalId, date);
        expTimeSlots = List.of();
        checkTimeSlotsLists(expTimeSlots, foundTimeSlots);
    }

    private void checkTimeSlotsLists(List<TimeSlot> expectTimes, List<TimeSlot> foundTimes) {
        assertTrue(expectTimes.size() == foundTimes.size());

        for (int i = 0; i < expectTimes.size(); i++) {
            var timeSlotExpect = expectTimes.get(i);
            var timeSlotFound = foundTimes.get(i);

            assertEquals(timeSlotExpect.getStartTime(), timeSlotFound.getStartTime());
            assertEquals(timeSlotExpect.getEndTime(), timeSlotFound.getEndTime());
            assertEquals(timeSlotExpect.isAvailable(), timeSlotFound.isAvailable());
        }
    }
}
