package com.epam.recreation_module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streetId;

    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private District district;
}
