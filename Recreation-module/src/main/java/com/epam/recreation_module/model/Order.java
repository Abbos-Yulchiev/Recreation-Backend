package com.epam.recreation_module.model;

import com.epam.recreation_module.model.enums.OrderFor;
import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_list")
public class Order extends AbsEntity {

    @ManyToOne
    private User user;

    private LocalDateTime creationDate;

    private LocalDateTime bookingDate;

    private boolean paid;

    @Enumerated(EnumType.STRING)
    private OrderFor orderFor; // For Ticket or Recreation Place

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    private double totalPrice;
}
