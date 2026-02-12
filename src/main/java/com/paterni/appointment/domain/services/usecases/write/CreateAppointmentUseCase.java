package com.paterni.appointment.domain.services.usecases.write;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paterni.appointment.domain.entities.Appointment;
import com.paterni.appointment.domain.entities.AppointmentStatus;
import com.paterni.appointment.domain.entities.AppointmentType;
import com.paterni.appointment.domain.entities.Area;
import com.paterni.appointment.domain.entities.Client;
import com.paterni.appointment.domain.entities.Professional;
import com.paterni.appointment.domain.models.TimeSlot;
import com.paterni.appointment.domain.repositories.AppointmentRepository;
import com.paterni.appointment.domain.repositories.AppointmentTypeRepository;
import com.paterni.appointment.domain.repositories.AreaRepository;
import com.paterni.appointment.domain.repositories.ClientRepository;
import com.paterni.appointment.domain.repositories.ProfessionalRepository;
import com.paterni.appointment.domain.services.exceptions.BusinessException;
import com.paterni.appointment.domain.services.usecases.read.SearchProfessionalAvailabiltyTimesUseCase;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private ClientRepository clienteRepository;

    @Autowired
    private SearchProfessionalAvailabiltyTimesUseCase searchProfessionalAvailabiltyTimesUseCase;

    public Appointment executeUserCase(Appointment appointment) {
        checkAppointmentTypeExistOrThrowsException(appointment.getAppointmentType());
        checkAreaExistOrThrowsException(appointment.getArea());

        Professional professional = getProfessionalIfExistOrThrowsException(appointment.getProfessional());
        checkProfessionalActiveOrThrowsException(professional);
        checkAssociationBetweenProfessionalAndAreaOrThrowsException(professional, appointment.getArea());

        checkProfessionalCanCreateAppointmentAtDateAndTimeOrThrowsException(professional, appointment);
        checkProfessionalHasAvailableScheduleOrThrowsException(professional, appointment);

        checkAppointmentIsNowOrFutureOrThrowsException(appointment.getDate(), appointment.getStartTime());
        Client client = getClientIfExistOrThrowsException(appointment.getClient());

        checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(client, appointment);
        return this.appointmentRepository.save(appointment);

    }

    private void checkProfessionalHasAvailableScheduleOrThrowsException(Professional professional,
            Appointment appointment) {
        List<TimeSlot> timeSlots = this.searchProfessionalAvailabiltyTimesUseCase.executeUseCase(professional,
                appointment.getDate());

        if (timeSlots.isEmpty()) {
            throw new BusinessException("Professional has no available schedule at the given date.");
        } else {
            var timeSlot = timeSlots.stream().filter(ts -> ts.getStartTime().equals(appointment.getStartTime()) &&
                    ts.getEndTime().equals(appointment.getEndTime())).findFirst();

            if (timeSlot.isEmpty()) {
                throw new BusinessException("Professional has no available schedule at the given date and time.");
            }
        }

    }

    private void checkProfessionalCanCreateAppointmentAtDateAndTimeOrThrowsException(Professional professional,
            Appointment appointment) {
        if (this.appointmentRepository.existsAppointmentsForProfessional(professional,
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                List.of(AppointmentStatus.OPEN, AppointmentStatus.PRESENT))) {
            throw new BusinessException(
                    "Professional already has an open or present appointment at the given date and time.");
        }
    }

    private void checkClientCanCreateAppointmentAtDateAndTimeOrThrowsException(Client client, Appointment appointment) {
        if (this.appointmentRepository.existsAppointmentsForClient(client,
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                List.of(AppointmentStatus.OPEN, AppointmentStatus.PRESENT))) {
            throw new BusinessException(
                    "Client already has an open or present appointment at the given date and time.");
        }
    }

    private void checkAssociationBetweenProfessionalAndAreaOrThrowsException(Professional professional, Area area) {
        if (!this.professionalRepository.existsAssocioationWithArea(professional.getId(), area.getId())) {
            throw new BusinessException("Professional doesn't have association with area");
        }
    }

    private void checkProfessionalActiveOrThrowsException(Professional professional) {
        if (!professional.isActive()) {
            throw new BusinessException("Professional is not active");
        }
    }

    private void checkAppointmentIsNowOrFutureOrThrowsException(LocalDate date, LocalTime startTime) {
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException("The date is in the past.");
        } else {
            if (date.equals(LocalDate.now()) && startTime.isBefore(LocalTime.now())) {
                throw new BusinessException("The time is in the past.");
            }
        }
    }

    private void checkAppointmentTypeExistOrThrowsException(AppointmentType appointmentType) {
        if (!appointmentTypeRepository.existsById(appointmentType.getId())) {
            throw new EntityNotFoundException("Appointment type does not exist");
        }
    }

    private void checkAreaExistOrThrowsException(Area area) {
        if (!areaRepository.existsById(area.getId())) {
            throw new EntityNotFoundException("Area does not exist");
        }
    }

    private Professional getProfessionalIfExistOrThrowsException(Professional professional) {
        return professionalRepository.findById(professional.getId())
                .orElseThrow(() -> new EntityNotFoundException("Professional does not exist"));

    }

    private Client getClientIfExistOrThrowsException(Client client) {
        return clienteRepository.findById(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("Client does not exist"));

    }

}
