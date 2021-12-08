package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.OrderFor;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.model.enums.RoleName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
class OrderRepositoryTest {

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

    @Test
    void getUserLastOrder() {
        Order order = new Order();

        Role role = new Role(1, RoleName.ADMIN.toString(),
                "Controller person of the system");
        Role savedRole = roleRepository.save(role);
        User user = new User(
                "11111112",
                "username",
                "Alex",
                "Mango",
                "123",
                Collections.singleton(savedRole)
        );

        User savedUser = userRepository.save(user);


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

        order.setOrderFor(OrderFor.RECREATION);
        order.setTotalPrice(12.5);
        order.setBookingDate(LocalDateTime.now());
        order.setPaid(true);
        order.setUser(savedUser);
        order.setTickets(new LinkedList<>());
        order.setCreationDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        Assertions.assertEquals(Optional.of(savedOrder), orderRepository.getUserLastOrder(user.getCitizenId()));
        assertEquals(orderRepository.getUserLastOrder(user.getCitizenId()).get().getUser(), savedUser);
    }
}