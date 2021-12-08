package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.TicketDTO;
import com.epam.recreation_module.model.Event;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.EventRepository;
import com.epam.recreation_module.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Done
 */
@ContextConfiguration(classes = {TicketServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TicketServiceImplTest {

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;
    User user = new User();
    Event event = new Event();
    TicketDTO ticketDTO = new TicketDTO();

    @BeforeEach
    void setUp() {
        user.setLastName("James");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("12345678");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Bond");
        user.setRoles(new HashSet<>());

        event.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setUpdatedAt(mock(Timestamp.class));
        event.setRecreations(new ArrayList<>());
        event.setName("Just an event");
        event.setEventType(EventType.CONCERT);
        event.setIsDeleted(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setDescription("The characteristics of someone or something");
        event.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setConfirmed(true);
        event.setUpdatedBy(user);
        event.setAvailableSits(1);
        event.setId(123L);
        event.setCreatedAt(mock(Timestamp.class));
        event.setEventStatus(EventStatus.ACTIVE);
        event.setCreatedBy(user);

        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
    }

    @Test
    void testGetAllByEventId() {
        when(this.ticketRepository.getTicketByEventIds(ArgumentMatchers.any())).thenReturn(1L);
        Optional<Event> ofResult = Optional.of(event);
        when(this.eventRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        ResponseEntity<?> actualAllByEventId = this.ticketServiceImpl.getAllByEventId(123L);
        assertEquals("<200 OK OK,1,[]>", actualAllByEventId.toString());
        assertTrue(actualAllByEventId.hasBody());
        assertEquals(HttpStatus.OK, actualAllByEventId.getStatusCode());
        assertTrue(actualAllByEventId.getHeaders().isEmpty());
        verify(ticketRepository).getTicketByEventIds(ArgumentMatchers.any());
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testGetAllByEventId2() {
        when(this.ticketRepository.getTicketByEventIds(ArgumentMatchers.any())).thenReturn(1L);
        when(this.eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.ticketServiceImpl.getAllByEventId(123L));
        verify(this.eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testGetUnSoldTickets() {
        when(ticketRepository.unSoldTickets(ArgumentMatchers.any())).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualUnSoldTickets = ticketServiceImpl.getUnSoldTickets(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Ok, success=true, roles=null, object=[]),[]>",
                actualUnSoldTickets.toString());
        assertTrue(actualUnSoldTickets.hasBody());
        assertTrue(actualUnSoldTickets.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUnSoldTickets.getStatusCode());
        assertTrue(((Collection<Object>) ((ApiResponse) actualUnSoldTickets.getBody()).getObject()).isEmpty());
        assertEquals("Ok", ((ApiResponse) actualUnSoldTickets.getBody()).getMessage());
        assertTrue(((ApiResponse) actualUnSoldTickets.getBody()).isSuccess());
        verify(this.ticketRepository).unSoldTickets(ArgumentMatchers.any());
    }

    @Test
    void testGetUnSoldTickets2() {
        when(this.ticketRepository.unSoldTickets(ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        ResponseEntity<?> actualUnSoldTickets = ticketServiceImpl.getUnSoldTickets(123L);
        assertEquals("Error", actualUnSoldTickets.getBody());
        assertEquals("<409 CONFLICT Conflict,Error,[]>", actualUnSoldTickets.toString());
        assertEquals(HttpStatus.CONFLICT, actualUnSoldTickets.getStatusCode());
        assertTrue(actualUnSoldTickets.getHeaders().isEmpty());
        verify(this.ticketRepository).unSoldTickets(ArgumentMatchers.any());
    }

    @Test
    void testNewTickets() {
        when(this.ticketRepository.saveAll(ArgumentMatchers.any())).thenReturn(new ArrayList<>());
        Optional<Event> ofResult = Optional.of(event);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        ResponseEntity<?> actualNewTicketsResult = ticketServiceImpl.newTickets(ticketDTO);
        assertEquals("Tickets successfully added.", actualNewTicketsResult.getBody());
        assertEquals("<201 CREATED Created,Tickets successfully added.,[]>", actualNewTicketsResult.toString());
        assertEquals(HttpStatus.CREATED, actualNewTicketsResult.getStatusCode());
        assertTrue(actualNewTicketsResult.getHeaders().isEmpty());
        verify(ticketRepository).saveAll(ArgumentMatchers.any());
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testNewTickets2() {
        ticketDTO.setQuantities(2);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        when(ticketRepository.saveAll(ArgumentMatchers.any())).thenReturn(new ArrayList<>());
        Optional<Event> ofResult = Optional.of(event);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        ResponseEntity<?> actualNewTicketsResult = ticketServiceImpl.newTickets(ticketDTO);
        assertEquals("Available places are not sufficient", actualNewTicketsResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Available places are not sufficient,[]>", actualNewTicketsResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualNewTicketsResult.getStatusCode());
        assertTrue(actualNewTicketsResult.getHeaders().isEmpty());
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testEditTickets() {

        when(this.ticketRepository.editingTicket(ArgumentMatchers.anyDouble(), ArgumentMatchers.any())).thenReturn(new ArrayList<>());
        Optional<Event> ofResult = Optional.of(event);
        when(this.eventRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        ResponseEntity<?> actualEditTicketsResult = this.ticketServiceImpl.editTickets(ticketDTO);
        assertEquals("Ticket price edited", actualEditTicketsResult.getBody());
        assertEquals("<202 ACCEPTED Accepted,Ticket price edited,[]>", actualEditTicketsResult.toString());
        assertEquals(HttpStatus.ACCEPTED, actualEditTicketsResult.getStatusCode());
        assertTrue(actualEditTicketsResult.getHeaders().isEmpty());
        verify(ticketRepository).editingTicket(ArgumentMatchers.anyDouble(), ArgumentMatchers.any());
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testEditTickets2() {
        when(ticketRepository.editingTicket(ArgumentMatchers.anyDouble(), ArgumentMatchers.any())).thenReturn(new ArrayList<>());
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> ticketServiceImpl.editTickets(ticketDTO));
        verify(eventRepository).findById(ArgumentMatchers.any());
    }
}

