package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.ConfirmEventDTO;
import com.epam.recreation_module.model.DTO.EventDTO;
import com.epam.recreation_module.service.EventService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Class {@code EventController} is an endpoint of the API which used for Events
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/event"
 * Annotated by {@link CrossOrigin} with no parameters to give permission to connection
 * So that {@code EventController} is accessed by sending requests to /event
 *
 * @author Yulchiev Abbos
 * @since 1.0
 */
@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {

    final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    /**
     * Getting an Event by ID method
     * Annotated by ApiOperation with parameters:
     * value = "Get an Event by ID"
     * Annotated by {@link GetMapping} with parameter value = /get/{eventId}
     *
     * @param eventId - Event ID which  wanted to get
     * @return HTTP response with event
     */
    @ApiOperation(value = "Get an Event by ID")
    @GetMapping(value = "/get/{eventId}")
    public ResponseEntity<?> getEventById(@ApiParam("Event id") @PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }


    /**
     * Getting all events by page method
     * Annotated by {@link ApiOperation} with parameters: value = "Get all Events by page"
     * Annotated by {@link GetMapping} with parameter value = /all
     *
     * @param page - Event page
     * @param size - page's size
     * @return HTTP response with Event page
     */
    @ApiOperation(value = "Get all Events by page)")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllEvent(@ApiParam("Event page") @RequestParam(defaultValue = "0", required = false) Integer page,
                                         @ApiParam("Page size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return eventService.getAllEvent(page, size);
    }

    /**
     * Getting all exist Events by page method
     * Annotated by {@link ApiOperation} with parameters: value = "Get all Events which do not delete for users by page"
     * Annotated by {@link GetMapping} with parameter value = /allNotDelete
     *
     * @param page -  event page
     * @param size -  page's size
     * @return HTTP response with Event page
     */
    @ApiOperation(value = "Get all Events which do not delete for users by page")
    @GetMapping(value = "/allNotDelete")
    public ResponseEntity<?> getEventNotDelete(@ApiParam("Event page") @RequestParam(defaultValue = "0", required = false) Integer page,
                                               @ApiParam(value = "Page's size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return eventService.getEventNotDelete(page, size);
    }


    /**
     * Creating a new Event method
     * Annotated by {@link ApiOperation} with parameters: value = "Creating a new Event"
     * Annotated by {@link PostMapping} with parameter value = /add
     *
     * @param eventDTO - object to create new event
     * @return HTTP response with success or failure message
     */
    @ApiOperation(value = "Creating a new Event")
    @PostMapping(value = "/add")
    public ResponseEntity<?> addAnEvent(@ApiParam("EventDTO object") @RequestBody EventDTO eventDTO) {
        return eventService.addAnEvent(eventDTO);
    }

    /**
     * Edit an Event by Event ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Edit an Event by id"
     * Annotated by {@link PutMapping} with parameter value = /edit/{eventId}
     *
     * @param eventId  - Event ID which wanted to edit
     * @param eventDTO - object to edit an Event
     * @return HTTP response with success or failure message
     */
    @ApiOperation(value = "Edit an Event by id")
    @PutMapping(value = "/edit/{eventId}")
    public ResponseEntity<?> editEvent(@ApiParam("Event ID") @PathVariable Long eventId,
                                       @ApiParam("EventDTO object") @RequestBody EventDTO eventDTO) {
        return eventService.editEvent(eventId, eventDTO);
    }

    /**
     * Delete an Event by Event ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Delete Event by id"
     * Annotated by {@link DeleteMapping} with parameter value = /delete/{eventId}
     *
     * @param eventId - Event ID which wanted to delete
     * @return HTTP response with success or failure message
     */
    @ApiOperation(value = "Delete Event by id")
    @DeleteMapping(value = "/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@ApiParam("Event ID") @PathVariable Long eventId) {
        return eventService.deleteEvent(eventId);

    }

    /**
     * Confirm Event from City Management System ID method
     * Annotated by {@link ApiOperation} with parameters: value = "Confirm Event. It work with City management module"
     * Annotated by {@link PostMapping} with parameter value = /confirmEvent
     *
     * @param body - object to confirm payment
     * @return HTTP response with Event ID
     */
    @ApiIgnore
    @ApiOperation(value = "Confirm Event. It work with City management module")
    @PostMapping(value = "/confirmEvent")
    public ResponseEntity<?> confirmEvent(@ApiParam("ConfirmEventDTO object") @RequestBody ConfirmEventDTO body) {
        return eventService.confirmEvent(body);
    }
}
