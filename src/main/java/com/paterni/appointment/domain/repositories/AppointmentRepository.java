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
import com.paterni.appointment.domain.models.TimeSlot;

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

    @Query(value = """
            WITH RECURSIVE SequencialCTE (cont) AS (
                SELECT 0
                UNION ALL
                SELECT cont + 1
                FROM SequencialCTE
                WHERE cont < 31
            ),
            WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week) AS (
                SELECT
                    start_time,
                    DATEADD(MINUTE, slot_size, start_time),
                    slot_size,
                    end_time,
                    day_of_week
                FROM TBL_WORK_SCHEDULE_ITEM
                WHERE PROFESSIONAL_ID = :professional_id

                UNION ALL

                SELECT
                    DATEADD(MINUTE, _slot_size * 1, _start_time),
                    DATEADD(MINUTE, _slot_size, _start_time_plus_inc),
                    _slot_size,
                    _end_time,
                    _day_of_week
                FROM WorkSchedule
                WHERE _start_time < DATEADD(MINUTE, -_slot_size, _end_time)
            )
            SELECT DISTINCT DAY(work_day_availability)
            FROM WorkSchedule
            JOIN (
                SELECT DATEADD('DAY', cont, TRIM(:start)) AS work_day_availability
                FROM (
                    SELECT cont
                    FROM SequencialCTE
                ) AS sq
                WHERE cont <= DATEDIFF('DAY', :start, :end)
            ) AS dates
                ON DAY_OF_WEEK(work_day_availability) = _day_of_week
            LEFT JOIN TBL_APPOINTMENT A
                ON A.PROFESSIONAL_ID = :professional_id
                AND A.START_TIME < _start_time_plus_inc
                AND A.END_TIME > _start_time
                AND A.DATE = work_day_availability
                AND (A.STATUS = 'OPEN' OR A.STATUS = 'PRESENT')
            WHERE A.ID IS NULL
                                    """, nativeQuery = true)
    public List<Integer> getAvailableDayFromProfessional(long professional_id, LocalDate start, LocalDate end);

    @Query(value = """
            WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week)
              AS  (
                SELECT
                    start_time,
                    DATEADD(MINUTE, slot_size, start_time),
                    slot_size,
                    end_time,
                    day_of_week
                FROM TBL_WORK_SCHEDULE_ITEM
                WHERE PROFESSIONAL_ID = :professional_id
                    AND day_of_week = DAY_OF_WEEK(:date)

                UNION ALL

                SELECT
                    DATEADD(MINUTE, _slot_size * 1, _start_time),
                    DATEADD(MINUTE, _slot_size, _start_time_plus_inc),
                    _slot_size,
                    _end_time,
                    _day_of_week
                FROM WorkSchedule
                WHERE _start_time < DATEADD(MINUTE, -_slot_size, _end_time)
            )
            SELECT

                ws._start_time  AS startTime,
                ws._start_time_plus_inc AS endTime,
                CASE WHEN A.date IS NULL THEN TRUE ELSE FALSE END AS available

            FROM WorkSchedule ws
            LEFT JOIN TBL_APPOINTMENT AS A
                ON A.PROFESSIONAL_ID = :professional_id
                AND A.date = :date
                AND A.START_TIME < ws._start_time_plus_inc
                AND A.END_TIME > ws._start_time
                AND A.STATUS IN ('OPEN', 'PRESENT')

            order by startTime
                        """, nativeQuery = true)
    public List<TimeSlot> getAvailableTimeFromProfessional(long professional_id, LocalDate date);
}
