package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderRecreation extends AbsEntity {

    @Column(nullable = false)
    private Long recreationId;

    @Column(nullable = false)
    private double price;

    private Long orderId;

    @Column(nullable = false)
    private Integer visitorsNumber;

    @Column(nullable = false)
    private LocalDateTime visitingTime;

    //Visiting time count with minutes
    @Column(nullable = false)
    private LocalDateTime leavingTime;
}
