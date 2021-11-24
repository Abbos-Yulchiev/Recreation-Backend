package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.TicketDTO;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.EventRepository;
import com.epam.recreation_module.repository.TicketRepository;
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

@ContextConfiguration(classes = {TicketServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TicketServiceImplTest {
    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Test
    void testGetAllByEventId() {
        when(this.ticketRepository.getTicketByEventIds((Long) ArgumentMatchers.any())).thenReturn(1L);

        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("42");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setRoles(new HashSet<Role>());

        Event event = new Event();
        event.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setUpdatedAt(mock(Timestamp.class));
        event.setRecreations(new ArrayList<Recreation>());
        event.setName("Name");
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
        Optional<Event> ofResult = Optional.<Event>of(event);
        when(this.eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
        ResponseEntity<?> actualAllByEventId = this.ticketServiceImpl.getAllByEventId(123L);
        assertEquals("<200 OK OK,1,[]>", actualAllByEventId.toString());
        assertTrue(actualAllByEventId.hasBody());
        assertEquals(HttpStatus.OK, actualAllByEventId.getStatusCode());
        assertTrue(actualAllByEventId.getHeaders().isEmpty());
        verify(this.ticketRepository).getTicketByEventIds((Long) ArgumentMatchers.any());
        verify(this.eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetAllByEventId2() {
        when(this.ticketRepository.getTicketByEventIds((Long) ArgumentMatchers.any())).thenReturn(1L);
        when(this.eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Event>empty());
        assertThrows(NotFoundException.class, () -> this.ticketServiceImpl.getAllByEventId(123L));
        verify(this.eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetUnSoldTickets() {
        when(this.ticketRepository.unSoldTickets((Long) ArgumentMatchers.any())).thenReturn(new ArrayList<Ticket>());
        ResponseEntity<?> actualUnSoldTickets = this.ticketServiceImpl.getUnSoldTickets(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Ok, success=true, roles=null, object=[]),[]>",
                actualUnSoldTickets.toString());
        assertTrue(actualUnSoldTickets.hasBody());
        assertTrue(actualUnSoldTickets.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUnSoldTickets.getStatusCode());
        assertTrue(((Collection<Object>) ((ApiResponse) actualUnSoldTickets.getBody()).getObject()).isEmpty());
        assertEquals("Ok", ((ApiResponse) actualUnSoldTickets.getBody()).getMessage());
        assertTrue(((ApiResponse) actualUnSoldTickets.getBody()).isSuccess());
        verify(this.ticketRepository).unSoldTickets((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetUnSoldTickets2() {
        when(this.ticketRepository.unSoldTickets((Long) ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        ResponseEntity<?> actualUnSoldTickets = this.ticketServiceImpl.getUnSoldTickets(123L);
        assertEquals("Error", actualUnSoldTickets.getBody());
        assertEquals("<409 CONFLICT Conflict,Error,[]>", actualUnSoldTickets.toString());
        assertEquals(HttpStatus.CONFLICT, actualUnSoldTickets.getStatusCode());
        assertTrue(actualUnSoldTickets.getHeaders().isEmpty());
        verify(this.ticketRepository).unSoldTickets((Long) ArgumentMatchers.any());
    }

    @Test
    void testNewTickets() {
        when(this.ticketRepository.saveAll((Iterable<Ticket>) ArgumentMatchers.any())).thenReturn(new ArrayList<Ticket>());

        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("42");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setRoles(new HashSet<Role>());

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setAccountNonLocked(true);
        user1.setAccountNonExpired(true);
        user1.setUsername("janedoe");
        user1.setCredentialsNonExpired(true);
        user1.setCitizenId("42");
        user1.setId(123L);
        user1.setDeleted(true);
        user1.setEnabled(true);
        user1.setFirstName("Jane");
        user1.setRoles(new HashSet<Role>());

        Event event = new Event();
        event.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setUpdatedAt(mock(Timestamp.class));
        event.setRecreations(new ArrayList<Recreation>());
        event.setName("Name");
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
        event.setCreatedBy(user1);
        Optional<Event> ofResult = Optional.<Event>of(event);
        when(this.eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        ResponseEntity<?> actualNewTicketsResult = this.ticketServiceImpl.newTickets(ticketDTO);
        assertEquals("Tickets successfully added.", actualNewTicketsResult.getBody());
        assertEquals("<201 CREATED Created,Tickets successfully added.,[]>", actualNewTicketsResult.toString());
        assertEquals(HttpStatus.CREATED, actualNewTicketsResult.getStatusCode());
        assertTrue(actualNewTicketsResult.getHeaders().isEmpty());
        verify(this.ticketRepository).saveAll((Iterable<Ticket>) ArgumentMatchers.any());
        verify(this.eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testNewTickets2() {
        when(this.ticketRepository.saveAll((Iterable<Ticket>) ArgumentMatchers.any())).thenReturn(new ArrayList<Ticket>());

        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("12346578");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Firstname");
        user.setRoles(new HashSet<Role>());


        Event event = new Event();
        event.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setUpdatedAt(mock(Timestamp.class));
        event.setRecreations(new ArrayList<Recreation>());
        event.setName("Name");
        event.setEventType(EventType.CONCERT);
        event.setIsDeleted(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setDescription("The characteristics of someone or something");
        event.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setConfirmed(true);
        event.setUpdatedBy(user);
        event.setAvailableSits(0);
        event.setId(123L);
        event.setCreatedAt(mock(Timestamp.class));
        event.setEventStatus(EventStatus.ACTIVE);
        event.setCreatedBy(user);
        Optional<Event> ofResult = Optional.<Event>of(event);
        when(this.eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        ResponseEntity<?> actualNewTicketsResult = this.ticketServiceImpl.newTickets(ticketDTO);
        assertEquals("Available places are not sufficient", actualNewTicketsResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Available places are not sufficient,[]>", actualNewTicketsResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualNewTicketsResult.getStatusCode());
        assertTrue(actualNewTicketsResult.getHeaders().isEmpty());
        verify(this.eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testEditTickets() {
        when(this.ticketRepository.editingTicket(ArgumentMatchers.anyDouble(), (Long) ArgumentMatchers.any())).thenReturn(new ArrayList<Integer>());

        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("12345678");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Firstname");
        user.setRoles(new HashSet<Role>());


        Event event = new Event();
        event.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setUpdatedAt(mock(Timestamp.class));
        event.setRecreations(new ArrayList<Recreation>());
        event.setName("Name");
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
        Optional<Event> ofResult = Optional.<Event>of(event);
        when(this.eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        ResponseEntity<?> actualEditTicketsResult = this.ticketServiceImpl.editTickets(ticketDTO);
        assertEquals("Ticket price edited", actualEditTicketsResult.getBody());
        assertEquals("<202 ACCEPTED Accepted,Ticket price edited,[]>", actualEditTicketsResult.toString());
        assertEquals(HttpStatus.ACCEPTED, actualEditTicketsResult.getStatusCode());
        assertTrue(actualEditTicketsResult.getHeaders().isEmpty());
        verify(this.ticketRepository).editingTicket(ArgumentMatchers.anyDouble(), (Long) ArgumentMatchers.any());
        verify(this.eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testEditTickets2() {
        when(this.ticketRepository.editingTicket(ArgumentMatchers.anyDouble(), (Long) ArgumentMatchers.any())).thenReturn(new ArrayList<Integer>());
        when(this.eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Event>empty());

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        assertThrows(NotFoundException.class, () -> this.ticketServiceImpl.editTickets(ticketDTO));
        verify(this.eventRepository).findById((Long) ArgumentMatchers.any());
    }
}

