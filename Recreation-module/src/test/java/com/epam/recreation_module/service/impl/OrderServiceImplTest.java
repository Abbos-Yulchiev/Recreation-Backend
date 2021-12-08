package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.OrderRecreationDTO;
import com.epam.recreation_module.model.DTO.OrderTicketDTO;
import com.epam.recreation_module.model.DTO.PaymentConfirmationDTO;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.model.enums.OrderFor;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.AddressRepository;
import com.epam.recreation_module.repository.BuildingRepository;
import com.epam.recreation_module.repository.DistrictRepository;
import com.epam.recreation_module.repository.EventRepository;
import com.epam.recreation_module.repository.HMACSecretKeyRepository;
import com.epam.recreation_module.repository.MadeOrderRepository;
import com.epam.recreation_module.repository.OrderRecreationRepository;
import com.epam.recreation_module.repository.OrderRepository;
import com.epam.recreation_module.repository.OrderedTicketsRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import com.epam.recreation_module.repository.RoleRepository;
import com.epam.recreation_module.repository.StreetRepository;
import com.epam.recreation_module.repository.TicketRepository;
import com.epam.recreation_module.repository.UserRepository;
import com.epam.recreation_module.repository.UsersOrderedRecreationsRepository;
import com.epam.recreation_module.security.hmac.HMACUtil;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Done
 */
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    User user = new User();
    User user1 = new User();
    Order order = new Order();
    Order order1 = new Order();
    Ticket ticket = new Ticket();
    Event event = new Event();


    @Test
    void setUp() {
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

        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.RECREATION);
        order.setTickets(new ArrayList<Ticket>());
        order.setTotalPrice(10.0);

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

        order1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setUser(user1);
        order1.setPaid(true);
        order1.setId(123L);
        order1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderFor(OrderFor.RECREATION);
        order1.setTickets(new ArrayList<Ticket>());
        order1.setTotalPrice(10.0);

        ticket.setOrder(order1);
        ticket.setEventId(123L);
        ticket.setPrice(10.0);
        ticket.setId(123L);
        ticket.setBought(true);

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
    }

    @Test
    void testGetMyTickets() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        when(orderedTicketsRepository.findOrderedTicketsByUser(any())).thenReturn(new ArrayList<>());
        OrderRepository orderRepository = mock(OrderRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualMyTickets = (new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class)))
                .getMyTickets();
        assertEquals("<200 OK OK,ApiResponse(message=My Recreations, success=true, roles=null, object=[]),[]>",
                actualMyTickets.toString());
        assertTrue(actualMyTickets.hasBody());
        assertTrue(actualMyTickets.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualMyTickets.getStatusCode());
        assertTrue(((Collection<Object>) ((ApiResponse) actualMyTickets.getBody()).getObject()).isEmpty());
        assertEquals("My Recreations", ((ApiResponse) actualMyTickets.getBody()).getMessage());
        assertTrue(((ApiResponse) actualMyTickets.getBody()).isSuccess());
        verify(orderedTicketsRepository).findOrderedTicketsByUser(any());
    }

    @Test
    void testGetMyTickets2() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        when(orderedTicketsRepository.findOrderedTicketsByUser(any()))
                .thenThrow(new NotFoundException("An error occurred"));
        OrderRepository orderRepository = mock(OrderRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualMyTickets = (new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class)))
                .getMyTickets();
        assertEquals("User not found!", actualMyTickets.getBody());
        assertEquals("<404 NOT_FOUND Not Found,User not found!,[]>", actualMyTickets.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualMyTickets.getStatusCode());
        assertTrue(actualMyTickets.getHeaders().isEmpty());
        verify(orderedTicketsRepository).findOrderedTicketsByUser(any());
    }

    @Test
    void testGetMyRecreations() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        UsersOrderedRecreationsRepository usersOrderedRecreationsRepository = mock(UsersOrderedRecreationsRepository.class);
        when(usersOrderedRecreationsRepository.userOrderedRecreations( any()))
                .thenReturn(new ArrayList<>());
        OrderRepository orderRepository = mock(OrderRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualMyRecreations = (new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class), usersOrderedRecreationsRepository,
                mock(MadeOrderRepository.class), mock(UserRepository.class))).getMyRecreations();
        assertEquals("<200 OK OK,ApiResponse(message=My Recreations, success=true, roles=null, object=[]),[]>",
                actualMyRecreations.toString());
        assertTrue(actualMyRecreations.hasBody());
        assertTrue(actualMyRecreations.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualMyRecreations.getStatusCode());
        assertTrue(((Collection<Object>) ((ApiResponse) actualMyRecreations.getBody()).getObject()).isEmpty());
        assertEquals("My Recreations", ((ApiResponse) actualMyRecreations.getBody()).getMessage());
        assertTrue(((ApiResponse) actualMyRecreations.getBody()).isSuccess());
        verify(usersOrderedRecreationsRepository).userOrderedRecreations((Long) any());
    }

    @Test
    void testGetOrdersByRecreationId() {
        MadeOrderRepository madeOrderRepository = mock(MadeOrderRepository.class);
        when(madeOrderRepository.findAllByOrderByRecreationId(any()))
                .thenReturn(new ArrayList<>());
        OrderRepository orderRepository = mock(OrderRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualOrdersByRecreationId = (new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), madeOrderRepository, mock(UserRepository.class)))
                .getOrdersByRecreationId(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Orders, success=true, roles=null, object=[]),[]>",
                actualOrdersByRecreationId.toString());
        assertTrue(actualOrdersByRecreationId.hasBody());
        assertTrue(actualOrdersByRecreationId.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualOrdersByRecreationId.getStatusCode());
        assertTrue(((Collection<Object>) ((ApiResponse) actualOrdersByRecreationId.getBody()).getObject()).isEmpty());
        assertEquals("Orders", ((ApiResponse) actualOrdersByRecreationId.getBody()).getMessage());
        assertTrue(((ApiResponse) actualOrdersByRecreationId.getBody()).isSuccess());
        verify(madeOrderRepository).findAllByOrderByRecreationId(any());
    }

    @Test
    void testOrderTicket() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(order);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAll(any())).thenReturn(new ArrayList<>());
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        OrderTicketDTO orderTicketDTO = new OrderTicketDTO();
        orderTicketDTO.setTicketsId(new ArrayList<>());
        ResponseEntity<?> actualOrderTicketResult = orderServiceImpl.orderTicket(orderTicketDTO);
        assertEquals("<202 ACCEPTED Accepted,[]>", actualOrderTicketResult.toString());
        assertFalse(actualOrderTicketResult.hasBody());
        assertEquals(HttpStatus.ACCEPTED, actualOrderTicketResult.getStatusCode());
        assertTrue(actualOrderTicketResult.getHeaders().isEmpty());
        verify(orderRepository).save(any());
        verify(ticketRepository).saveAll(any());
    }

    @Test
    void testOrderTicket2() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(order);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAll(any())).thenThrow(new NotFoundException("An error occurred"));
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        OrderTicketDTO orderTicketDTO = new OrderTicketDTO();
        orderTicketDTO.setTicketsId(new ArrayList<>());
        ResponseEntity<?> actualOrderTicketResult = orderServiceImpl.orderTicket(orderTicketDTO);
        assertEquals("Error occurred. Not found!", actualOrderTicketResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Error occurred. Not found!,[]>", actualOrderTicketResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualOrderTicketResult.getStatusCode());
        assertTrue(actualOrderTicketResult.getHeaders().isEmpty());
        verify(orderRepository).save(any());
        verify(ticketRepository).saveAll(any());
    }

    @Test
    void testOrderTicket4() {


        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(order);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));
        when(ticketRepository.saveAll(any())).thenReturn(new ArrayList<>());
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(any())).thenThrow(new NotFoundException("An error occurred"));
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(0L);

        OrderTicketDTO orderTicketDTO = new OrderTicketDTO();
        orderTicketDTO.setTicketsId(resultLongList);
        ResponseEntity<?> actualOrderTicketResult = orderServiceImpl.orderTicket(orderTicketDTO);
        assertEquals("Error occurred. Not found!", actualOrderTicketResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Error occurred. Not found!,[]>", actualOrderTicketResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualOrderTicketResult.getStatusCode());
        assertTrue(actualOrderTicketResult.getHeaders().isEmpty());
        verify(ticketRepository).findById(any());
        verify(eventRepository).findById(any());
    }

    @Test
    void testOrderTicket5() {

        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(order);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(any())).thenReturn(Optional.empty());
        when(ticketRepository.saveAll(any())).thenReturn(new ArrayList<>());

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(0L);

        OrderTicketDTO orderTicketDTO = new OrderTicketDTO();
        orderTicketDTO.setTicketsId(resultLongList);
        ResponseEntity<?> actualOrderTicketResult = orderServiceImpl.orderTicket(orderTicketDTO);
        assertEquals("Error occurred. Not found!", actualOrderTicketResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Error occurred. Not found!,[]>", actualOrderTicketResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualOrderTicketResult.getStatusCode());
        assertTrue(actualOrderTicketResult.getHeaders().isEmpty());
        verify(ticketRepository).findById(any());
    }

    @Test
    void testOrderRecreation() {
        User user = mock(User.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById(any())).thenReturn(Optional.empty());
        OrderRepository orderRepository = mock(OrderRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                recreationRepository, mock(OrderRecreationRepository.class), mock(UsersOrderedRecreationsRepository.class),
                mock(MadeOrderRepository.class), mock(UserRepository.class));

        OrderRecreationDTO orderRecreationDTO = new OrderRecreationDTO();
        orderRecreationDTO.setVisitingDuration(10.0);
        orderRecreationDTO.setVisitorsNumber(10);
        orderRecreationDTO.setRecreationId(123L);
        orderRecreationDTO.setVisitingTime(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> orderServiceImpl.orderRecreation(orderRecreationDTO));
        verify(recreationRepository).findById(any());
    }

    /*@Test
    void testPayForOrder() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setRedirectUrl("https://example.org/example");
        paymentDTO.setOrderId(123L);
        ResponseEntity<?> actualPayForOrderResult = orderServiceImpl.payForOrder(paymentDTO);
        assertEquals("Error occurred", actualPayForOrderResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Error occurred,[]>", actualPayForOrderResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualPayForOrderResult.getStatusCode());
        assertTrue(actualPayForOrderResult.getHeaders().isEmpty());
    }*/

    @Test
    void testPaymentConfirmation() {
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
        user.setRoles(new HashSet<>());

        Order order = new Order();
        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.RECREATION);
        order.setTickets(new ArrayList<>());
        order.setTotalPrice(10.0);
        Optional<Order> ofResult = Optional.of(order);

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
        user1.setRoles(new HashSet<>());

        Order order1 = new Order();
        order1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setUser(user1);
        order1.setPaid(true);
        order1.setId(123L);
        order1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderFor(OrderFor.RECREATION);
        order1.setTickets(new ArrayList<>());
        order1.setTotalPrice(10.0);
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderRepository.getUserLastOrder(any())).thenReturn(ofResult);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        PaymentConfirmationDTO paymentConfirmationDTO = new PaymentConfirmationDTO();
        paymentConfirmationDTO.setMessage("Not all who wander are lost");
        paymentConfirmationDTO.setCitizenCardId("42");
        paymentConfirmationDTO.setAmount(10.0);
        paymentConfirmationDTO.setTransactionId("42");
        paymentConfirmationDTO.setSuccess(true);
        ResponseEntity<?> actualPaymentConfirmationResult = orderServiceImpl.paymentConfirmation(paymentConfirmationDTO);
        assertEquals("<200 OK OK,ApiResponse(message=Payment is successfully made!, success=true, roles=null," + " object=null),[]>",
                actualPaymentConfirmationResult.toString());
        assertTrue(actualPaymentConfirmationResult.hasBody());
        assertTrue(actualPaymentConfirmationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualPaymentConfirmationResult.getStatusCode());
        assertEquals("Payment is successfully made!",
                ((ApiResponse) Objects.requireNonNull(actualPaymentConfirmationResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualPaymentConfirmationResult.getBody()).isSuccess());
        verify(orderRepository).getUserLastOrder(any());
        verify(orderRepository).save(any());
    }

    /*@Test
    void testPaymentConfirmation2() {
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
        user.setRoles(new HashSet<>());

        Order order = new Order();
        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.RECREATION);
        order.setTickets(new ArrayList<>());
        order.setTotalPrice(10.0);
        Optional<Order> ofResult = Optional.of(order);
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenThrow(new NotFoundException("An error occurred"));
        when(orderRepository.getUserLastOrder(any())).thenReturn(ofResult);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        PaymentConfirmationDTO paymentConfirmationDTO = new PaymentConfirmationDTO();
        paymentConfirmationDTO.setMessage("Not all who wander are lost");
        paymentConfirmationDTO.setCitizenCardId("42");
        paymentConfirmationDTO.setAmount(10.0);
        paymentConfirmationDTO.setTransactionId("42");
        paymentConfirmationDTO.setSuccess(true);
        ResponseEntity<?> actualPaymentConfirmationResult = orderServiceImpl.paymentConfirmation(paymentConfirmationDTO);
        assertEquals("Error occurred", actualPaymentConfirmationResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Error occurred,[]>", actualPaymentConfirmationResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualPaymentConfirmationResult.getStatusCode());
        assertTrue(actualPaymentConfirmationResult.getHeaders().isEmpty());
        verify(orderRepository).getUserLastOrder(any());
        verify(orderRepository).save(any());
    }*/

    @Test
    void testPaymentConfirmation3() {
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
        user.setRoles(new HashSet<>());

        Order order = new Order();
        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.TICKET);
        order.setTickets(new ArrayList<>());
        order.setTotalPrice(10.0);
        Optional<Order> ofResult = Optional.of(order);

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
        user1.setRoles(new HashSet<>());

        Order order1 = new Order();
        order1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setUser(user1);
        order1.setPaid(true);
        order1.setId(123L);
        order1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderFor(OrderFor.RECREATION);
        order1.setTickets(new ArrayList<>());
        order1.setTotalPrice(10.0);
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any())).thenReturn(order1);
        when(orderRepository.getUserLastOrder(any())).thenReturn(ofResult);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(ticketRepository.findAllByOrder(any())).thenReturn(new ArrayList<>());
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class),
                mock(UsersOrderedRecreationsRepository.class), mock(MadeOrderRepository.class), mock(UserRepository.class));

        PaymentConfirmationDTO paymentConfirmationDTO = new PaymentConfirmationDTO();
        paymentConfirmationDTO.setMessage("Not all who wander are lost");
        paymentConfirmationDTO.setCitizenCardId("42");
        paymentConfirmationDTO.setAmount(10.0);
        paymentConfirmationDTO.setTransactionId("42");
        paymentConfirmationDTO.setSuccess(true);
        ResponseEntity<?> actualPaymentConfirmationResult = orderServiceImpl.paymentConfirmation(paymentConfirmationDTO);
        assertEquals(
                "<200 OK OK,ApiResponse(message=Payment is successfully made!, success=true, roles=null," + " object=null),[]>",
                actualPaymentConfirmationResult.toString());
        assertTrue(actualPaymentConfirmationResult.hasBody());
        assertTrue(actualPaymentConfirmationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualPaymentConfirmationResult.getStatusCode());
        assertEquals("Payment is successfully made!",
                ((ApiResponse) Objects.requireNonNull(actualPaymentConfirmationResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualPaymentConfirmationResult.getBody()).isSuccess());
        verify(orderRepository).getUserLastOrder(any());
        verify(orderRepository).save(any());
        verify(ticketRepository).findAllByOrder(any());
        verify(ticketRepository).saveAll(any());
    }

    @Test
    void testCancelOrder3() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        doNothing().when(orderRepository).deleteById(any());
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(ticketRepository.findAllByOrder(any())).thenReturn(new ArrayList<>());

        OrderRecreation orderRecreation = new OrderRecreation();
        orderRecreation.setPrice(10.0);
        orderRecreation.setId(123L);
        orderRecreation.setVisitingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setVisitorsNumber(10);
        orderRecreation.setRecreationId(123L);
        orderRecreation.setLeavingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setOrderId(123L);
        OrderRecreationRepository orderRecreationRepository = mock(OrderRecreationRepository.class);
        doNothing().when(orderRecreationRepository).deleteById(any());
        when(orderRecreationRepository.findOrderRecreationByOrderId(any())).thenReturn(orderRecreation);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        assertThrows(NotFoundException.class,
                () -> (new OrderServiceImpl(orderRepository, ticketRepository, orderedTicketsRepository, eventRepository,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                        mock(RecreationRepository.class), orderRecreationRepository, mock(UsersOrderedRecreationsRepository.class),
                        mock(MadeOrderRepository.class), mock(UserRepository.class))).cancelOrder(123L));
        verify(orderRepository).findById(any());
    }

    @Test
    void testCancelOrder4() {
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
        user.setRoles(new HashSet<>());

        Order order = new Order();
        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.TICKET);
        order.setTickets(new ArrayList<>());
        order.setTotalPrice(10.0);
        Optional<Order> ofResult = Optional.of(order);
        OrderRepository orderRepository = mock(OrderRepository.class);
        doNothing().when(orderRepository).deleteById(any());
        when(orderRepository.findById(any())).thenReturn(ofResult);

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
        user1.setRoles(new HashSet<>());

        Order order1 = new Order();
        order1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setUser(user1);
        order1.setPaid(true);
        order1.setId(123L);
        order1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderFor(OrderFor.RECREATION);
        order1.setTickets(new ArrayList<>());
        order1.setTotalPrice(10.0);

        Ticket ticket = new Ticket();
        ticket.setOrder(order1);
        ticket.setEventId(123L);
        ticket.setPrice(10.0);
        ticket.setId(123L);
        ticket.setBought(true);

        ArrayList<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(ticketRepository.findAllByOrder(any())).thenReturn(ticketList);

        OrderRecreation orderRecreation = new OrderRecreation();
        orderRecreation.setPrice(10.0);
        orderRecreation.setId(123L);
        orderRecreation.setVisitingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setVisitorsNumber(10);
        orderRecreation.setRecreationId(123L);
        orderRecreation.setLeavingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setOrderId(123L);
        OrderRecreationRepository orderRecreationRepository = mock(OrderRecreationRepository.class);
        doNothing().when(orderRecreationRepository).deleteById(any());
        when(orderRecreationRepository.findOrderRecreationByOrderId(any())).thenReturn(orderRecreation);
        OrderedTicketsRepository orderedTicketsRepository = mock(OrderedTicketsRepository.class);
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualCancelOrderResult = (new OrderServiceImpl(orderRepository, ticketRepository,
                orderedTicketsRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1),
                mock(RecreationRepository.class), orderRecreationRepository, mock(UsersOrderedRecreationsRepository.class),
                mock(MadeOrderRepository.class), mock(UserRepository.class))).cancelOrder(123L);
        assertEquals(
                "<202 ACCEPTED Accepted,ApiResponse(message=Order canceled!, success=true, roles=null," + " object=null),[]>",
                actualCancelOrderResult.toString());
        assertTrue(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.ACCEPTED, actualCancelOrderResult.getStatusCode());
        assertEquals("Order canceled!", ((ApiResponse) Objects.requireNonNull(actualCancelOrderResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualCancelOrderResult.getBody()).isSuccess());
        verify(orderRepository).deleteById(any());
        verify(orderRepository).findById(any());
        verify(ticketRepository).findAllByOrder(any());
        verify(ticketRepository).saveAll(any());
    }
}
