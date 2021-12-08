package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.TicketDTO;
import com.epam.recreation_module.model.Event;
import com.epam.recreation_module.model.Ticket;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.EventRepository;
import com.epam.recreation_module.repository.TicketRepository;
import com.epam.recreation_module.service.TicketService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    final TicketRepository ticketRepository;
    final EventRepository eventRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, @Lazy EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<?> getAllByEventId(Long eventId) {
        eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Invalid Event Id!"));
        Long count = ticketRepository.getTicketByEventIds(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @Override
    public ResponseEntity<?> getUnSoldTickets(Long eventId) {
        List<Ticket> unSoldTickets = ticketRepository.unSoldTickets(eventId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ticket list.", true, unSoldTickets));
    }

    @Override
    public ResponseEntity<?> newTickets(TicketDTO ticketDTO) {
        Long eventId = ticketDTO.getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Invalid event Id"));
        Integer sits = event.getAvailableSits();
        if (sits >= ticketDTO.getQuantities()) {
            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < ticketDTO.getQuantities(); i++) {
                tickets.add(new Ticket(ticketDTO.getPrice(), ticketDTO.getEventId()));
            }
            ticketRepository.saveAll(tickets);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tickets successfully added.");
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Available places are not sufficient");
    }

    @Override
    public ResponseEntity<?> editTickets(TicketDTO ticketDTO) {
        eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new NotFoundException("Invalid event Id"));
        ticketRepository.editingTicket(ticketDTO.getPrice(), ticketDTO.getEventId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Ticket price edited");
    }
}