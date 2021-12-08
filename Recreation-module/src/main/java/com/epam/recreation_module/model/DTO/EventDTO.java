package com.epam.recreation_module.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private String name;

    private String description;

    private List<String> startTime;

    private List<String> endTime;

    private Integer availableSits;

    private String eventType;

    private String eventStatus;

    private List<Long> recreationId;
}
