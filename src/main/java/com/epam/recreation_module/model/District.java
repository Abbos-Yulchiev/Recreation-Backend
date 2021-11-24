package com.epam.recreation_module.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    private Integer id;

    @Column(nullable = false)
    private String name;

}
