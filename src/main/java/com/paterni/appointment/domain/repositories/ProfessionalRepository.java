package com.paterni.appointment.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paterni.appointment.domain.entities.Professional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    /**
     * @param professionalId
     * @param areaId
     * @return true se existir uma associação entre o profissional e a área, caso contrário, false.
     * @apiNote Verifica se existe uma associação entre um profissional e uma área específica.
     */
    @Query("SELECT COUNT(p) > 0 FROM Professional p JOIN p.areas a WHERE p.id = :professionalId AND a.id = :areaId")
    boolean existsAssocioationWithArea(Long professionalId, Integer areaId);
    
}
