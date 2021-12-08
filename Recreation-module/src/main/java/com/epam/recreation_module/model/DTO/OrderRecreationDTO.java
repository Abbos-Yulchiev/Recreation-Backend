package com.epam.recreation_module.model.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRecreationDTO {

    private Long recreationId;

    private Integer visitorsNumber;

    private double visitingDuration;

    private List<String> visitingTime;
}
