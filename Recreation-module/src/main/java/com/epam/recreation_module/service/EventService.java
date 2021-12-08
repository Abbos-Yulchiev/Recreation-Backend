package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.ConfirmEventDTO;
import com.epam.recreation_module.model.DTO.EventDTO;
import org.springframework.http.ResponseEntity;

public interface EventService {

    ResponseEntity<?> getEventById(Long eventId);

    ResponseEntity<?> getAllEvent(Integer page, Integer size);

    ResponseEntity<?> addAnEvent(EventDTO eventDTO);

    ResponseEntity<?> getEventNotDelete(Integer page, Integer size);

    ResponseEntity<?> editEvent(Long eventId, EventDTO eventDTO);

    ResponseEntity<?> editEventStatus(Long eventId, String status);

    ResponseEntity<?> deleteEvent(Long eventId);

    ResponseEntity<?> confirmEvent(ConfirmEventDTO body);
}
