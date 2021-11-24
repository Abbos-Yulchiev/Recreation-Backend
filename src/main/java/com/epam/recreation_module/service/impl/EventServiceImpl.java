package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.ConfirmEventDTO;
import com.epam.recreation_module.model.DTO.EventDTO;
import com.epam.recreation_module.model.Event;
import com.epam.recreation_module.model.Recreation;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.EventRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import com.epam.recreation_module.service.EventService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    final EventRepository eventRepository;
    final RecreationRepository recreationRepository;
    final ExternalConnections externalConnections;

    public EventServiceImpl(EventRepository eventRepository, @Lazy RecreationRepository recreationRepository,
                            ExternalConnections externalConnections) {
        this.eventRepository = eventRepository;
        this.recreationRepository = recreationRepository;
        this.externalConnections = externalConnections;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found!"));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Success", true, event));
    }

    @Override
    public ResponseEntity<?> getAllEvent(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(eventPage);
    }

    @Override
    public ResponseEntity<?> getEventNotDelete(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAllConfirmed(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(eventPage);
    }

    @Override
    public ResponseEntity<?> addAnEvent(EventDTO eventDTO) {

        Event newEvent = new Event();
        List<Recreation> eventList = new ArrayList<>();
        StringBuilder insufficientRecreationsBuilder = new StringBuilder();
        for (Long recreationId : eventDTO.getRecreationId()) {
            Recreation recreation = recreationRepository.findById(recreationId)
                    .orElseThrow(() -> new NotFoundException("Invalid Recreation Id given!"));
            Integer availableSits = recreation.getAvailableSits();
            if (eventDTO.getAvailableSits() > availableSits)
                insufficientRecreationsBuilder.append(recreation).append("; ");
            eventList.add(recreation);
        }
        String insufficientRecreations = insufficientRecreationsBuilder.toString();
        if (insufficientRecreations.length() != 0)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Available sits are insufficient! Recreation Id(s): " + insufficientRecreations, false));
        return eventSetter(eventDTO, eventList, newEvent);
    }

    @Override
    public ResponseEntity<?> editEvent(Long eventId, EventDTO eventDTO) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found!"));

        List<Recreation> eventList = new ArrayList<>();
        StringBuilder insufficientRecreationBuilder = new StringBuilder();
        for (Long recreationId : eventDTO.getRecreationId()) {
            Recreation recreation = recreationRepository.findById(recreationId)
                    .orElseThrow(() -> new NotFoundException("Invalid Recreation Id given! Id:" + recreationId));
            Integer availableSits = recreation.getAvailableSits();
            if (eventDTO.getAvailableSits() > availableSits)
                insufficientRecreationBuilder.append(recreationId).append("; ");
            eventList.add(recreation);
        }
        String insufficientRecreations = insufficientRecreationBuilder.toString();
        if (insufficientRecreations.length() != 0)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Available sits are insufficient! Recreation Id(s): " + insufficientRecreations, false));
        return eventSetter(eventDTO, eventList, event);
    }

    @Override
    public ResponseEntity<?> deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found!", eventId));
        if (event.getId() != null) {
            LocalDateTime now = LocalDateTime.now();
            event.setIsDeleted(now);
            eventRepository.save(event);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Event deleted!", true));
    }

    @Override
    public ResponseEntity<?> confirmEvent(ConfirmEventDTO body) {
        try {
            Event event = eventRepository.findById(body.getEventId())
                    .orElseThrow(() -> new NotFoundException("Event not found!", body.getEventId()));
            if (event.isConfirmed())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Event already confirmed!");
            event.setConfirmed(body.isConfirmed());
            eventRepository.save(event);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("New event: confirmed!\nID: " + body.getEventId());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Event! Event ID:" + body.getEventId());
        }
    }

    private ResponseEntity<?> eventSetter(EventDTO eventDTO, List<Recreation> eventList, Event event) {

        String[] startTimes = eventDTO.getStartTime();
        String startTime = startTimes[0];
        String[] endTimes = eventDTO.getEndTime();
        String endTime = endTimes[0];

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        event.setName(eventDTO.getName());
        event.setAvailableSits(eventDTO.getAvailableSits());
        event.setDescription(eventDTO.getDescription());
        event.setStartTime(LocalDateTime.parse(startTime, format));
        event.setEndTime(LocalDateTime.parse(endTime, format));
        event.setEventStatus(EventStatus.valueOf(eventDTO.getEventStatus().toUpperCase()));
        event.setEventType(EventType.valueOf(eventDTO.getEventType().toUpperCase()));
        event.setRecreations(eventList);
        Event save = eventRepository.save(event);
        Long id = save.getId();
        ResponseEntity<?> response = externalConnections.creatingNewEvent(String.valueOf(id), event.getName(),
                eventDTO.getDescription(), startTime, endTime);
        return ResponseEntity.status(response.getStatusCode())
                .body(new ApiResponse("The event saved. Event Id: " + save.getId(), true));
    }
}
