package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RecreationRepositoryTest {

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

    Recreation recreation = new Recreation();
    Address address = new Address();

    @BeforeEach
    void setUp() {
        District district = new District();
        Street street = new Street();
        Building building = new Building();

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
        Address savedAddress = addressRepository.save(address);

        recreation.setPrice(2.0);
        recreation.setRecreationCategory(RecreationCategory.PARK);
        recreation.setRecreationStatus(RecreationStatus.OPEN);
        recreation.setDescription("NewRecreation");
        recreation.setAvailableSits(10);
        recreation.setName("Disney");
        recreation.setExist(true);
        recreation.setOpeningTime(LocalDateTime.now());
        recreation.setClosingTime(LocalDateTime.now().plusYears(10));
        recreation.setAddress(savedAddress);
        recreationRepository.save(recreation);

    }

    @AfterEach
    void tearDown() {
        recreationRepository.deleteAll();
        addressRepository.deleteAll();
        districtRepository.deleteAll();
        streetRepository.deleteAll();
        buildingRepository.deleteAll();
    }

    @Test
    void findByIdAndExistTrue() {
        assertEquals(Optional.of(recreation), recreationRepository.findByIdAndExistTrue(recreation.getId()));
    }

    @Test
    void findAllByExistTrue() {
        Assertions.assertEquals(1, recreationRepository.findAllByExistTrue(Pageable.ofSize(1)).getSize());
    }

    @Test
    void existsByNameAndAddress_Id() {
        assertTrue(recreationRepository.existsByNameAndAddress_Id("Disney", address.getId()));
    }

    @Test
    void findByCategory() {
        assertEquals(1, recreationRepository.findByCategory(RecreationCategory.PARK.toString(), Pageable.unpaged()).getTotalElements());
    }
}