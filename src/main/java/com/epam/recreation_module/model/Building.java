package com.epam.recreation_module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buildingId;

    private Integer id;

    @ManyToOne
    private Street street;

    @Column(nullable = false)
    private Integer buildingNumber;

    @Column(nullable = false)
    private String buildingType;
}
