package com.epam.recreation_module.model.DTO;

import lombok.Data;

@Data
public class TicketDTO {

    private double price;

    private int quantities;

    private Long eventId;
}
