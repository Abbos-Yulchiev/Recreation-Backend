package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.ConfirmEventDTO;
import com.epam.recreation_module.model.DTO.EventDTO;
import com.epam.recreation_module.model.Event;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.security.hmac.HMACUtil;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Done checked
 */
class EventServiceImplTest {

    User user = new User();
    Event event = new Event();

    @BeforeEach
    void init(){
        user.setId(123L);
        user.setCitizenId("12345678");
        user.setFirstName("Bond");
        user.setLastName("James");
        user.setUsername("username");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setRoles(new HashSet<>());

        event.setName("Name");
        event.setDescription("The characteristics of someone or something");
        event.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setUpdatedAt(mock(Timestamp.class));
        event.setRecreations(new ArrayList<>());
        event.setEventType(EventType.CONCERT);
        event.setIsDeleted(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        event.setConfirmed(true);
        event.setUpdatedBy(user);
        event.setAvailableSits(1);
        event.setId(123L);
        event.setCreatedAt(mock(Timestamp.class));
        event.setEventStatus(EventStatus.ACTIVE);
        event.setCreatedBy(user);
    }

    @Test
    void testGetEventById() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        assertThrows(NotFoundException.class,
                () -> (new EventServiceImpl(eventRepository, recreationRepository,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository1, restTemplate,
                                        addressRepository, buildingRepository,
                                        streetRepository, districtRepository,
                                        new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                        .getEventById(123L));
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testGetAllEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        ResponseEntity<?> actualAllEvent = (new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).getAllEvent(1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAllEvent.toString());
        assertTrue(((PageImpl) Objects.requireNonNull(actualAllEvent.getBody())).toList().isEmpty());
        assertTrue(actualAllEvent.hasBody());
        assertEquals(HttpStatus.OK, actualAllEvent.getStatusCode());
        assertTrue(actualAllEvent.getHeaders().isEmpty());
        verify(eventRepository).findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testGetEventNotDelete() {
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAllConfirmed(ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        ResponseEntity<?> actualEventNotDelete = (new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).getEventNotDelete(1,
                3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualEventNotDelete.toString());
        assertTrue(((PageImpl) Objects.requireNonNull(actualEventNotDelete.getBody())).toList().isEmpty());
        assertTrue(actualEventNotDelete.hasBody());
        assertEquals(HttpStatus.OK, actualEventNotDelete.getStatusCode());
        assertTrue(actualEventNotDelete.getHeaders().isEmpty());
        verify(eventRepository).findAllConfirmed(ArgumentMatchers.any());
    }

    @Test
    void testAddAnEvent() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
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
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(0L);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setRecreationId(resultLongList);
        assertThrows(NotFoundException.class, () -> eventServiceImpl.addAnEvent(eventDTO));
        verify(recreationRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testEditEvent() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));
        assertThrows(NotFoundException.class, () -> eventServiceImpl.editEvent(123L, new EventDTO()));
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testEditEvent2() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
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
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(0L);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setRecreationId(resultLongList);
        assertThrows(NotFoundException.class, () -> eventServiceImpl.editEvent(1L, eventDTO));
        verify(eventRepository).findById(ArgumentMatchers.any());
        verify(recreationRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testDeleteEvent() {

        Optional<Event> ofResult = Optional.of(event);

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.save(ArgumentMatchers.any())).thenReturn(event);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        ResponseEntity<?> actualDeleteEventResult = (new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).deleteEvent(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Event deleted!, success=true, roles=null, object=null),[]>",
                actualDeleteEventResult.toString());
        assertTrue(actualDeleteEventResult.hasBody());
        assertTrue(actualDeleteEventResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteEventResult.getStatusCode());
        assertEquals("Event deleted!", ((ApiResponse) Objects.requireNonNull(actualDeleteEventResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualDeleteEventResult.getBody()).isSuccess());
        verify(eventRepository).findById(ArgumentMatchers.any());
        verify(eventRepository).save(ArgumentMatchers.any());
    }

    @Test
    void testConfirmEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ConfirmEventDTO confirmEventDTO = new ConfirmEventDTO();
        confirmEventDTO.setEventId(123L);
        confirmEventDTO.setConfirmed(true);
        ResponseEntity<?> actualConfirmEventResult = eventServiceImpl.confirmEvent(confirmEventDTO);
        assertEquals("Event already confirmed!", actualConfirmEventResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Event already confirmed!,[]>", actualConfirmEventResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualConfirmEventResult.getStatusCode());
        assertTrue(actualConfirmEventResult.getHeaders().isEmpty());
        verify(eventRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testConfirmEvent2() {

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findById(ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
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
        EventServiceImpl eventServiceImpl = new EventServiceImpl(eventRepository, recreationRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ConfirmEventDTO confirmEventDTO = new ConfirmEventDTO();
        confirmEventDTO.setEventId(1L);
        confirmEventDTO.setConfirmed(true);
        ResponseEntity<?> actualConfirmEventResult = eventServiceImpl.confirmEvent(confirmEventDTO);
        assertEquals("Invalid Event! Event ID:1", actualConfirmEventResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Invalid Event! Event ID:1,[]>", actualConfirmEventResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualConfirmEventResult.getStatusCode());
        assertTrue(actualConfirmEventResult.getHeaders().isEmpty());
        verify(eventRepository).findById(ArgumentMatchers.any());
    }
}

