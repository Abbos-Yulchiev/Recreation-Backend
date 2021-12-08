package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RecreationRepository recreationRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    StreetRepository streetRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EventRepository eventRepository;

    Event event = new Event();

    @BeforeEach
    public void setUp() {
        District district = new District();
        Street street = new Street();
        Building building = new Building();
        Address address = new Address();

        district.setId(1);
        district.setName("Name");
        districtRepository.save(district);

        street.setId(1);
        street.setName("Name");
        street.setDistrict(district);
        streetRepository.save(street);

        building.setStreet(street);
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");
        buildingRepository.save(building);

        address.setBuilding(building);
        address.setHomeNumber("42");
        address.setHomeCode(10);
        address.setId(123L);
        address.setOwnerCardNumber("42");
        addressRepository.save(address);

        Recreation recreation = new Recreation("Disney", "Description",
                10, LocalDateTime.now(), LocalDateTime.now().plusYears(10),
                RecreationCategory.GARDEN, RecreationStatus.OPEN, address, true, 10);
        recreationRepository.save(recreation);

        event.setEventType(EventType.PARTY);
        event.setEventStatus(EventStatus.ACTIVE);
        event.setConfirmed(true);
        event.setDescription("A new event");
        event.setAvailableSits(100);
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusDays(2));
        event.setRecreations(Collections.singletonList(recreation));
        event.setName("Black Friday");
        eventRepository.save(event);
    }

    @AfterEach
    public void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void getEventById() {
        Long id = event.getId();
        assertEquals(eventRepository.findById(id), Optional.of(event));
    }
}