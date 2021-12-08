package com.epam.recreation_module.model.payload;

import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class MadeOrdereRecreations extends AbsEntity {

    private LocalDateTime bookingDate;
    private LocalDateTime creationDate;
    private boolean paid;
    private double totalPrice;
    private String name;
    private double price;
    private LocalDateTime visitingTime;
    private LocalDateTime leavingTime;
    private int visitorsNumber;
    private String firstName;
    private String lastName;
}
