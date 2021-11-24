package com.epam.recreation_module.model;

import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.model.template.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Event extends UserDateAudit {

    private String name;

    private boolean confirmed;

    @Column(columnDefinition = "text")
    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer availableSits;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Recreation> recreations;

    private LocalDateTime isDeleted = null;
}
