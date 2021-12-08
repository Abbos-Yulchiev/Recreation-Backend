package com.epam.recreation_module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private Long id;

    private Integer homeCode;

    private String homeNumber;

    @ManyToOne
    private Building building;

    private String ownerCardNumber = "11111114";
}
