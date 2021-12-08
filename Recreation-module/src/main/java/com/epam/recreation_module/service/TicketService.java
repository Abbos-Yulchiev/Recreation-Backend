package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.TicketDTO;
import org.springframework.http.ResponseEntity;

public interface TicketService {

    ResponseEntity<?> getAllByEventId(Long eventId);

    ResponseEntity<?> newTickets(TicketDTO ticketDTO);

    ResponseEntity<?> getUnSoldTickets(Long eventId);

    ResponseEntity<?> editTickets(TicketDTO ticketDTO);
}
