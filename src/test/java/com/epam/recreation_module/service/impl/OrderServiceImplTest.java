package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.PaymentConfirmationDTO;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.OrderFor;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.security.hmac.HMACUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Test
    void testGetOrdersByRecreationId() {

        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.findAllByOrderByRecreationId((Long) ArgumentMatchers.any())).thenReturn(new ArrayList<Object>());
        TicketRepository ticketRepository = mock(TicketRepository.class);
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
        ResponseEntity<?> actualOrdersByRecreationId = (new OrderServiceImpl(orderRepository, ticketRepository,
                eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class))).getOrdersByRecreationId(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Orders, success=true, roles=null, object=[]),[]>",
                actualOrdersByRecreationId.toString());
        assertTrue(actualOrdersByRecreationId.hasBody());
        assertTrue(actualOrdersByRecreationId.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualOrdersByRecreationId.getStatusCode());
        assertTrue(((Collection<Object>) ((ApiResponse) actualOrdersByRecreationId.getBody()).getObject()).isEmpty());
        assertEquals("Orders", ((ApiResponse) actualOrdersByRecreationId.getBody()).getMessage());
        assertTrue(((ApiResponse) actualOrdersByRecreationId.getBody()).isSuccess());
        verify(orderRepository).findAllByOrderByRecreationId((Long) ArgumentMatchers.any());
    }

    @Test
    void testPaymentConfirmation() {
        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("1234568");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Firstname");
        user.setRoles(new HashSet<Role>());

        Order order = new Order();
        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.RECREATION);
        order.setTickets(new ArrayList<Ticket>());
        order.setTotalPrice(10.0);
        Optional<Order> ofResult = Optional.<Order>of(order);

        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save((Order) ArgumentMatchers.any())).thenReturn(order);
        when(orderRepository.getUserLastOrder((String) ArgumentMatchers.any())).thenReturn(ofResult);
        TicketRepository ticketRepository = mock(TicketRepository.class);
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
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, ticketRepository, eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))),
                mock(RecreationRepository.class), mock(OrderRecreationRepository.class));

        PaymentConfirmationDTO paymentConfirmationDTO = new PaymentConfirmationDTO();
        paymentConfirmationDTO.setMessage("Not all who wander are lost");
        paymentConfirmationDTO.setCitizenCardId("12345678");
        paymentConfirmationDTO.setAmount(10.0);
        paymentConfirmationDTO.setTransactionId("12");
        paymentConfirmationDTO.setSuccess(true);
        ResponseEntity<?> actualPaymentConfirmationResult = orderServiceImpl.paymentConfirmation(paymentConfirmationDTO);
        assertEquals(
                "<200 OK OK,ApiResponse(message=Payment is successfully made!, success=true, roles=null," + " object=null),[]>",
                actualPaymentConfirmationResult.toString());
        assertTrue(actualPaymentConfirmationResult.hasBody());
        assertTrue(actualPaymentConfirmationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualPaymentConfirmationResult.getStatusCode());
        assertEquals("Payment is successfully made!",
                ((ApiResponse) actualPaymentConfirmationResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualPaymentConfirmationResult.getBody()).isSuccess());
        verify(orderRepository).getUserLastOrder((String) ArgumentMatchers.any());
        verify(orderRepository).save((Order) ArgumentMatchers.any());
    }

    @Test
    void testCancelOrder() {
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

        Order order = new Order();
        order.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setUser(user);
        order.setPaid(true);
        order.setId(123L);
        order.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderFor(OrderFor.RECREATION);
        order.setTickets(new ArrayList<Ticket>());
        order.setTotalPrice(10.0);
        Optional<Order> ofResult = Optional.<Order>of(order);
        OrderRepository orderRepository = mock(OrderRepository.class);
        doNothing().when(orderRepository).deleteById((Long) ArgumentMatchers.any());
        when(orderRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);

        OrderRecreation orderRecreation = new OrderRecreation();
        orderRecreation.setPrice(10.0);
        orderRecreation.setId(123L);
        orderRecreation.setVisitingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setVisitorsNumber(10);
        orderRecreation.setRecreationId(123L);
        orderRecreation.setLeavingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setOrderId(123L);
        OrderRecreationRepository orderRecreationRepository = mock(OrderRecreationRepository.class);
        doNothing().when(orderRecreationRepository).deleteById((Long) ArgumentMatchers.any());
        when(orderRecreationRepository.findOrderRecreationByOrderId((Long) ArgumentMatchers.any())).thenReturn(orderRecreation);
        TicketRepository ticketRepository = mock(TicketRepository.class);
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
        ResponseEntity<?> actualCancelOrderResult = (new OrderServiceImpl(orderRepository, ticketRepository,
                eventRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))),
                mock(RecreationRepository.class), orderRecreationRepository)).cancelOrder(123L);
        assertEquals(
                "<202 ACCEPTED Accepted,ApiResponse(message=Order canceled!, success=false, roles=null," + " object=null),[]>",
                actualCancelOrderResult.toString());
        assertTrue(actualCancelOrderResult.hasBody());
        assertTrue(actualCancelOrderResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.ACCEPTED, actualCancelOrderResult.getStatusCode());
        assertEquals("Order canceled!", ((ApiResponse) actualCancelOrderResult.getBody()).getMessage());
        assertFalse(((ApiResponse) actualCancelOrderResult.getBody()).isSuccess());
        verify(orderRepository).deleteById((Long) ArgumentMatchers.any());
        verify(orderRepository).findById((Long) ArgumentMatchers.any());
        verify(orderRecreationRepository).deleteById((Long) ArgumentMatchers.any());
        verify(orderRecreationRepository).findOrderRecreationByOrderId((Long) ArgumentMatchers.any());
    }

    @Test
    void testCancelOrder2() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        doNothing().when(orderRepository).deleteById((Long) ArgumentMatchers.any());
        when(orderRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Order>empty());
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.saveAll((Iterable<Ticket>) ArgumentMatchers.any())).thenReturn(new ArrayList<Ticket>());
        when(ticketRepository.findAllByOrder((Order) ArgumentMatchers.any())).thenReturn(new ArrayList<Ticket>());

        OrderRecreation orderRecreation = new OrderRecreation();
        orderRecreation.setPrice(10.0);
        orderRecreation.setId(123L);
        orderRecreation.setVisitingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setVisitorsNumber(10);
        orderRecreation.setRecreationId(123L);
        orderRecreation.setLeavingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setOrderId(123L);
        OrderRecreationRepository orderRecreationRepository = mock(OrderRecreationRepository.class);
        doNothing().when(orderRecreationRepository).deleteById((Long) ArgumentMatchers.any());
        when(orderRecreationRepository.findOrderRecreationByOrderId((Long) ArgumentMatchers.any())).thenReturn(orderRecreation);
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
        assertThrows(NotFoundException.class,
                () -> (new OrderServiceImpl(orderRepository, ticketRepository, eventRepository,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null))),
                        mock(RecreationRepository.class), orderRecreationRepository)).cancelOrder(123L));
        verify(orderRepository).findById((Long) ArgumentMatchers.any());
    }

}
//TODO: getMyTickets(), getMyRecreations(), orderTickets(), orderRecreation(), payForOrder();

