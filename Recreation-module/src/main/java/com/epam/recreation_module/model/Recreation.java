package com.epam.recreation_module.model;

import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Recreation extends AbsEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private Integer availableSits;

    private LocalDateTime openingTime;

    private LocalDateTime closingTime;

    @Enumerated(EnumType.STRING)
    private RecreationCategory recreationCategory;

    @Enumerated(EnumType.STRING)
    private RecreationStatus recreationStatus;

    @OneToOne
    private Address address;

    private boolean exist = true;

    private double price; // an hourly visiting price for residents
}
