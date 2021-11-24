package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.TicketDTO;
import com.epam.recreation_module.service.TicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ApiOperation(value = "Get Ticket's list by event ID")
    @GetMapping("/allByEventId/{eventId}")
    public ResponseEntity<?> getAllByEventId(@ApiParam(name = "Event ID", type = "Long", example = "1") @PathVariable Long eventId) {
        return ticketService.getAllByEventId(eventId);
    }

    @ApiOperation(value = "Get unsold Ticket's list by event ID")
    @GetMapping("/getUnsoldTickets/{eventId}")
    public ResponseEntity<?> getUnsoldTickets(@ApiParam(name = "Event ID", type = "Long", example = "1") @PathVariable Long eventId) {
        return ticketService.getUnSoldTickets(eventId);
    }

    @ApiOperation(value = "This methods creates new ticket's list")
    @PostMapping("/create")
    public ResponseEntity<?> newTickets(@ApiParam(name = "Object", value = "Ticket") @RequestBody TicketDTO ticketDTO) {
        return ticketService.newTickets(ticketDTO);
    }

    @ApiOperation(value = "This methods edits ticket's list")
    @PutMapping("/edit")
    public ResponseEntity<?> editTickets(@ApiParam(name = "Object", value = "Ticket") @RequestBody TicketDTO ticketDTO) {
        return ticketService.editTickets(ticketDTO);
    }
}
