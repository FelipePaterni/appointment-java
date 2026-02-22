package com.paterni.appointment.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.paterni.appointment.domain.models.TimeSlot;

@Repository
@Profile({ "dev", "prod" })
public interface AppointmentRepositoryPostgres extends AppointmentRepository {

    /*
     * WITH RECURSIVE SequencialCTE (cont) AS (
     * SELECT 0
     * UNION ALL
     * SELECT cont + 1
     * FROM SequencialCTE
     * WHERE cont < 31
     * ),
     * WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time,
     * _day_of_week) AS (
     * SELECT
     * start_time,
     * start_time + MAKE_INTERVAL(mins => slot_size),
     * slot_size,
     * end_time,
     * day_of_week
     * FROM TBL_WORK_SCHEDULE_ITEM
     * WHERE PROFESSIONAL_ID = 6
     * 
     * UNION ALL
     * 
     * SELECT
     * _start_time + MAKE_INTERVAL(mins => _slot_size),
     * _start_time_plus_inc + MAKE_INTERVAL(mins => _slot_size),
     * _slot_size,
     * _end_time,
     * _day_of_week
     * FROM WorkSchedule
     * WHERE _start_time < _end_time - MAKE_INTERVAL(mins => _slot_size)
     * )
     * SELECT DISTINCT EXTRACT (DAY FROM work_day_availability) AS day_of_month
     * FROM WorkSchedule
     * JOIN (
     * SELECT DATE('2024-02-01 -03') + cont AS work_day_availability
     * FROM (
     * SELECT cont
     * FROM SequencialCTE
     * ) AS sq
     * WHERE cont <= DATE('2024-02-29 -03') - DATE('2024-02-01 -03')
     * ) AS dates
     * ON EXTRACT(DOW FROM work_day_availability) = _day_of_week
     * LEFT JOIN TBL_APPOINTMENT A
     * ON A.PROFESSIONAL_ID = 6
     * AND A.START_TIME < _start_time_plus_inc
     * AND A.END_TIME > _start_time
     * AND A.DATE = work_day_availability
     * AND (A.STATUS = 'OPEN' OR A.STATUS = 'PRESENT')
     * WHERE A.ID IS NULL
     */

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
            start_time + MAKE_INTERVAL(mins => slot_size),
                           slot_size,
                           end_time,
                           day_of_week
                       FROM TBL_WORK_SCHEDULE_ITEM
                       WHERE PROFESSIONAL_ID = :professionalId

                       UNION ALL

                       SELECT
               			_start_time + MAKE_INTERVAL(mins => _slot_size),
                           _start_time_plus_inc + MAKE_INTERVAL(mins => _slot_size),
                           _slot_size,
                           _end_time,
                           _day_of_week
                       FROM WorkSchedule
                       WHERE _start_time < _end_time - MAKE_INTERVAL(mins => _slot_size)
                   )
                   SELECT DISTINCT EXTRACT (DAY FROM work_day_availability) AS day_of_month
                   FROM WorkSchedule
                   JOIN (
                       SELECT DATE(:start) + cont AS work_day_availability
                       FROM (
                           SELECT cont
                           FROM SequencialCTE
                       ) AS sq
                       WHERE cont <= DATE(:end) - DATE(:start)
                   ) AS dates
                       ON EXTRACT(DOW FROM work_day_availability) = _day_of_week
                   LEFT JOIN TBL_APPOINTMENT A
                       ON A.PROFESSIONAL_ID = :professionalId
                       AND A.START_TIME < _start_time_plus_inc
                       AND A.END_TIME > _start_time
                       AND A.DATE = work_day_availability
                       AND (A.STATUS = 'OPEN' OR A.STATUS = 'PRESENT')
                   WHERE A.ID IS NULL
               """, nativeQuery = true)
    public List<Integer> getAvailableDaysFromProfessional(long professionalId, LocalDate start, LocalDate end);

    /*
     * WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size,
     * _end_time, _day_of_week)
     * AS (
     * SELECT
     * start_time,
     * start_time + MAKE_INTERVAL(mins=> slot_size) as _start_time_plus_inc,
     * slot_size,
     * end_time,
     * day_of_week
     * FROM TBL_WORK_SCHEDULE_ITEM
     * WHERE PROFESSIONAL_ID = 6
     * AND day_of_week = EXTRACT(DOW FROM DATE('2024-04-09 -03'))
     * 
     * UNION ALL
     * 
     * SELECT
     * _start_time + MAKE_INTERVAL(mins=> _slot_size),
     * _start_time_plus_inc + MAKE_INTERVAL(mins=> +_slot_size),
     * _slot_size,
     * _end_time,
     * _day_of_week
     * FROM WorkSchedule
     * WHERE _start_time < _end_time - MAKE_INTERVAL(mins=> _slot_size)
     * )
     * SELECT
     * 
     * ws._start_time AS startTime,
     * ws._start_time_plus_inc AS endTime,
     * CASE WHEN A.date IS NULL THEN TRUE ELSE FALSE END AS available
     * 
     * FROM WorkSchedule ws
     * LEFT JOIN TBL_APPOINTMENT AS A
     * ON A.PROFESSIONAL_ID = 6
     * AND A.date = DATE('2024-04-09 -03')
     * AND A.START_TIME < ws._start_time_plus_inc
     * AND A.END_TIME > ws._start_time
     * AND A.STATUS IN ('OPEN', 'PRESENT')
     * 
     * order by startTime
     */
    @Query(value = """
                   WITH RECURSIVE WorkSchedule (_start_time, _start_time_plus_inc, _slot_size, _end_time, _day_of_week)
                     AS  (
                       SELECT
                           start_time,
                           start_time + MAKE_INTERVAL(mins=> slot_size) as _start_time_plus_inc,
                           slot_size,
                           end_time,
                           day_of_week
                       FROM TBL_WORK_SCHEDULE_ITEM
                       WHERE PROFESSIONAL_ID = :professionalId
                           AND day_of_week = EXTRACT(DOW FROM DATE(:date))

                       UNION ALL

                       SELECT
            _start_time + MAKE_INTERVAL(mins=> _slot_size),
            _start_time_plus_inc + MAKE_INTERVAL(mins=> +_slot_size),
                           _slot_size,
                           _end_time,
                           _day_of_week
                       FROM WorkSchedule
                       WHERE _start_time <	_end_time - MAKE_INTERVAL(mins=> _slot_size)
                   )
                   SELECT

                       ws._start_time  AS startTime,
                       ws._start_time_plus_inc AS endTime,
                       CASE WHEN A.date IS NULL THEN TRUE ELSE FALSE END AS available

                   FROM WorkSchedule ws
                   LEFT JOIN TBL_APPOINTMENT AS A
                       ON A.PROFESSIONAL_ID = :professionalId
                       AND A.date = DATE(:date)
                       AND A.START_TIME < ws._start_time_plus_inc
                       AND A.END_TIME > ws._start_time
                       AND A.STATUS IN ('OPEN', 'PRESENT')

                   order by startTime
                               """, nativeQuery = true)
    public List<TimeSlot> getAvailableTimesFromProfessional(long professionalId, LocalDate date);

}
