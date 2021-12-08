package com.epam.recreation_module.model.payload;

import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderedTickets extends AbsEntity {

    private LocalDateTime bookingDate;

    private LocalDateTime creationDate;

    private boolean paid;

    private String name;

    private double price;
}
