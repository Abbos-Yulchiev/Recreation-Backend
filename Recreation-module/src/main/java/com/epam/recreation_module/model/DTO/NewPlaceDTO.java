package com.epam.recreation_module.model.DTO;

import lombok.Data;

@Data
public class NewPlaceDTO {

    private String name;
    private Integer homeId;
    private Long ownerCardNumber;
}
