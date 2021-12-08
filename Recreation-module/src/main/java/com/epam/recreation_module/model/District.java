package com.epam.recreation_module.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    private Integer id;

    @Column(nullable = false)
    private String name;

    public District(Integer districtId, Integer id, String name) {
        this.districtId = districtId;
        this.id = id;
        this.name = name;
    }
}
