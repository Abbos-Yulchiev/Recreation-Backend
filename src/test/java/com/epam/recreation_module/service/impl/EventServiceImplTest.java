package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.ConfirmEventDTO;
import com.epam.recreation_module.model.DTO.EventDTO;
import com.epam.recreation_module.model.Event;
import com.epam.recreation_module.model.Recreation;
import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.security.hmac.HMACUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    @Test
    void testGetEventById() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Event>empty());
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        assertThrows(NotFoundException.class,
                () -> (new EventServiceImpl(eventRepository, recreationRepository,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository1, restTemplate,
                                        addressRepository, buildingRepository,
                                        streetRepository, districtRepository,
                                        new ExternalConnections(null, null, null)))))
                        .getEventById(123L));
        verify(eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetAllEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<Event>(new ArrayList<Event>()));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualAllEvent = (new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).getAllEvent(1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAllEvent.toString());
        assertTrue(((PageImpl) actualAllEvent.getBody()).toList().isEmpty());
        assertTrue(actualAllEvent.hasBody());
        assertEquals(HttpStatus.OK, actualAllEvent.getStatusCode());
        assertTrue(actualAllEvent.getHeaders().isEmpty());
        verify(eventRepository).findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testGetEventNotDelete() {
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAllConfirmed((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<Event>(new ArrayList<Event>()));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualEventNotDelete = (new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).getEventNotDelete(1,
                3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualEventNotDelete.toString());
        assertTrue(((PageImpl) actualEventNotDelete.getBody()).toList().isEmpty());
        assertTrue(actualEventNotDelete.hasBody());
        assertEquals(HttpStatus.OK, actualEventNotDelete.getStatusCode());
        assertTrue(actualEventNotDelete.getHeaders().isEmpty());
        verify(eventRepository).findAllConfirmed((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testAddAnEvent() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Recreation>empty());
        EventRepository eventRepository = mock(EventRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))));

        ArrayList<Long> resultLongList = new ArrayList<Long>();
        resultLongList.add(0L);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setRecreationId(resultLongList);
        assertThrows(NotFoundException.class, () -> eventServiceImpl.addAnEvent(eventDTO));
        verify(recreationRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testEditEvent() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Event>empty());
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))));
        assertThrows(NotFoundException.class, () -> eventServiceImpl.editEvent(123L, new EventDTO()));
        verify(eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testEditEvent2() {
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
        event.setName("Event");
        event.setEventType(EventType.CONCERT);
        event.setIsDeleted(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setDescription("The characteristics of someone or something");
        event.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setConfirmed(true);
        event.setUpdatedBy(user);
        event.setAvailableSits(1);
        event.setId(1L);
        event.setCreatedAt(mock(Timestamp.class));
        event.setEventStatus(EventStatus.ACTIVE);
        event.setCreatedBy(user);
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Event>of(event));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Recreation>empty());
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))));

        ArrayList<Long> resultLongList = new ArrayList<Long>();
        resultLongList.add(0L);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setRecreationId(resultLongList);
        assertThrows(NotFoundException.class, () -> eventServiceImpl.editEvent(1L, eventDTO));
        verify(eventRepository).findById((Long) ArgumentMatchers.any());
        verify(recreationRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testDeleteEvent() {
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

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.save((Event) ArgumentMatchers.any())).thenReturn(event);
        when(eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualDeleteEventResult = (new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).deleteEvent(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Event deleted!, success=true, roles=null, object=null),[]>",
                actualDeleteEventResult.toString());
        assertTrue(actualDeleteEventResult.hasBody());
        assertTrue(actualDeleteEventResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteEventResult.getStatusCode());
        assertEquals("Event deleted!", ((ApiResponse) actualDeleteEventResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualDeleteEventResult.getBody()).isSuccess());
        verify(eventRepository).findById((Long) ArgumentMatchers.any());
        verify(eventRepository).save((Event) ArgumentMatchers.any());
    }

    @Test
    void testConfirmEvent() {
        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("1234");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("Username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("1234568");
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
        event.setId(1L);
        event.setCreatedAt(mock(Timestamp.class));
        event.setEventStatus(EventStatus.ACTIVE);
        event.setCreatedBy(user);
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Event>of(event));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))));

        ConfirmEventDTO confirmEventDTO = new ConfirmEventDTO();
        confirmEventDTO.setEventId(123L);
        confirmEventDTO.setConfirmed(true);
        ResponseEntity<?> actualConfirmEventResult = eventServiceImpl.confirmEvent(confirmEventDTO);
        assertEquals("Event already confirmed!", actualConfirmEventResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Event already confirmed!,[]>", actualConfirmEventResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualConfirmEventResult.getStatusCode());
        assertTrue(actualConfirmEventResult.getHeaders().isEmpty());
        verify(eventRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testConfirmEvent2() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById((Long) ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))));

        ConfirmEventDTO confirmEventDTO = new ConfirmEventDTO();
        confirmEventDTO.setEventId(1L);
        confirmEventDTO.setConfirmed(true);
        ResponseEntity<?> actualConfirmEventResult = eventServiceImpl.confirmEvent(confirmEventDTO);
        assertEquals("Invalid Event! Event ID:1", actualConfirmEventResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Invalid Event! Event ID:1,[]>", actualConfirmEventResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualConfirmEventResult.getStatusCode());
        assertTrue(actualConfirmEventResult.getHeaders().isEmpty());
        verify(eventRepository).findById((Long) ArgumentMatchers.any());
    }

}

