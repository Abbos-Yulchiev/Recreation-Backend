package com.epam.recreation_module.model.DTO;

import com.epam.recreation_module.model.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class OrderTicketDTO {

    private List<Long> ticketsId;

}
