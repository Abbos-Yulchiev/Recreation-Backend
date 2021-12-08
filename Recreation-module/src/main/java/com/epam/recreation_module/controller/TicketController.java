package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.TicketDTO;
import com.epam.recreation_module.service.TicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class {@code CommentaryController} is an endpoint of the API which used for <strong>Ticket</strong>
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/ticket"
 * Annotated by {@link CrossOrigin} with no parameters to <em>give permission to connection</em>
 * So that {@code TicketController} is accessed by sending requests to /ticket
 *
 * @author Yulchiev Abbos
 * @since 1.0
 * @version 1.0.0 Nov 25, 2021
 */
@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Get <strong>Tickets</strong> by Event's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get Ticket's list by event ID"
     * Annotated by {@link GetMapping} with parameter value = /allByEventId/{eventId}
     *
     * @param eventId Event ID
     * @return <em>HTTP</em>response with Ticket list of Event
     */
    @ApiOperation(value = "Get Ticket's list by event ID")
    @GetMapping("/allByEventId/{eventId}")
    public ResponseEntity<?> getAllByEventId(@ApiParam(name = "Event ID", type = "Long", example = "1")
                                             @PathVariable Long eventId) {
        return ticketService.getAllByEventId(eventId);
    }

    /**
     * Get unsold <strong>Ticket's</strong> of an Event
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get unsold Ticket's list by event ID"
     * Annotated by {@link GetMapping} with parameter value = /getUnsoldTickets/{eventId}
     * .
     *
     * @param eventId Event ID for Ticket's list
     * @return <em>HTTP</em> response with unsold Ticket list
     */
    @ApiOperation(value = "Get unsold Ticket's list by event ID")
    @GetMapping("/getUnsoldTickets/{eventId}")
    public ResponseEntity<?> getUnsoldTickets(@ApiParam(name = "Event ID", type = "Long", example = "1")
                                              @PathVariable Long eventId) {
        return ticketService.getUnSoldTickets(eventId);
    }

    /**
     * Creat new <strong>Ticket</strong> list for an Event
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method creates new ticket's list"
     * Annotated by {@link PostMapping} with parameter value = /create
     *
     * @param ticketDTO Object for create new Tickets
     * @return <em>HTTP</em> response wit message
     */
    @ApiOperation(value = "This method creates new ticket's list")
    @PostMapping("/create")
    public ResponseEntity<?> newTickets(@ApiParam(name = "Object", value = "Ticket")
                                        @RequestBody TicketDTO ticketDTO) {
        return ticketService.newTickets(ticketDTO);
    }

    /**
     * Edit Ticket's list
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method creates new ticket's list"
     * Annotated by {@link GetMapping} with parameter value = /edit
     *
     * @param ticketDTO Object for create new Tickets
     * @return <em>HTTP</em> response wit message
     */
    @ApiOperation(value = "This method edits ticket's list")
    @PutMapping("/edit")
    public ResponseEntity<?> editTickets(@ApiParam(name = "Object", value = "Ticket")
                                         @RequestBody TicketDTO ticketDTO) {
        return ticketService.editTickets(ticketDTO);
    }
}
