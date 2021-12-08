package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket extends AbsEntity {

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order = null;

    private boolean isBought = false;

    public Ticket(double price, Long eventId) {
        this.price = price;
        this.eventId = eventId;
    }
}
