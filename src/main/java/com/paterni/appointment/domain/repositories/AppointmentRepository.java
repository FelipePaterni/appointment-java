package com.paterni.appointment.domain.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.entities.AppointmentStatus;
import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.domain.entities.Professional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

        @Query("""
                        SELECT COUNT(a) > 0
                        FROM Appointment a
                        WHERE a.client = :client
                            AND a.date = :date
                            AND a.startTime < :endTime
                            AND a.endTime > :startTime
                            AND (
                                a.status = com.paterni.appointment.domain.entities.AppointmentStatus.OPEN
                                OR a.status = com.paterni.appointment.domain.entities.AppointmentStatus.PRESENT
                                )
                        """)
        boolean existsOpenOrPresentAppointmentsForClient(Client client, LocalDate date, LocalTime startTime,
                        LocalTime endTime);

        @Query("""
                            SELECT COUNT(a) > 0
                            FROM Appointment a
                            WHERE a.client = :client
                              AND a.date = :date
                              AND a.startTime < :endTime
                              AND a.endTime > :startTime
                              AND a.status IN :statuses
                        """)
        boolean existsAppointmentsForClient(Client client, LocalDate date, LocalTime startTime,
                        LocalTime endTime, List<AppointmentStatus> statuses);

        @Query("""
                            SELECT COUNT(a) > 0
                            FROM Appointment a
                            WHERE a.professional = :professional
                              AND a.date = :date
                              AND a.startTime < :endTime
                              AND a.endTime > :startTime
                              AND a.status IN :statuses
                        """)
        boolean existsAppointmentsForProfessional(Professional professional, LocalDate date, LocalTime startTime,
                        LocalTime endTime, List<AppointmentStatus> statuses);

        List<Appointment> findByProfessionalIdAndDate(Long id, LocalDate date);
}
