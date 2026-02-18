package com.paterni.appointment.domain.mappers;

import com.paterni.appointment.domain.entities.Area;
import com.paterni.appointment.dto.Area.AreaResponse;

public class AreaMapper {
    public static AreaResponse toAreaResponseDTO(Area area) {

        return new AreaResponse(
                area.getId(),
                area.getName());
    }

}
