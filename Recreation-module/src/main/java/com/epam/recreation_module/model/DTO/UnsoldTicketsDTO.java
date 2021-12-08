package com.epam.recreation_module.model.DTO;

import lombok.Data;

@Data
public class UnsoldTicketsDTO {

    private Long id;

    private double price;

    private Long event_id;
}
