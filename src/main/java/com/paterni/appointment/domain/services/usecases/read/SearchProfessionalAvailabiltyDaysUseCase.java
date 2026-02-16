package com.paterni.appointment.domain.services.usecases.read;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.entities.Professional;
import com.paterni.appointment.domain.services.exceptions.ParameterException;

@Service
public class SearchProfessionalAvailabiltyDaysUseCase {

    /*
     * @Autowired
     * private WorkScheduleItemRepository workScheduleItemRepository;
     * 
     * @Autowired
     * private AppointmentRepository appointmentRepository;
     */

    public List<Integer> executeUseCase(Professional professional, int month, int year) {

        checkMonthIsValidOrThrowsException(month);
        checkYearIsValidOrThrowsException(year);
        checkMonthAndCurrentYearAreValidOrThrowsException(month, year);

        Random random = new Random();
        List<Integer> randomList = new ArrayList<>();
        for (int i = 1; i <= 28; i++) {
            if (random.nextBoolean()) {
                randomList.add(i);
            }
        }
        return randomList;
    }

    private void checkMonthAndCurrentYearAreValidOrThrowsException(int month, int year) {
        if (LocalDate.now().getYear() == year && month < LocalDate.now().getMonthValue()) {
            throw new ParameterException(
                    "Invalid month for the current year. Month must be current month or in the future.");
        }
    }

    private void checkMonthIsValidOrThrowsException(int month) {
        if (month < 1 || month > 12) {
            throw new ParameterException("Invalid month. Month must be between 1 and 12.");
        }

    }

    private void checkYearIsValidOrThrowsException(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new ParameterException("Invalid year. Year must be current year or in the future.");
        }
    }

}
