package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Address;
import com.epam.recreation_module.model.Building;
import com.epam.recreation_module.model.District;
import com.epam.recreation_module.model.Street;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private AddressRepository addressRepository;

    Address address = new Address();

    @BeforeEach
    public void setUp() {

        District district = new District();
        Street street = new Street();
        Building building = new Building();

        district.setId(1);
        district.setName("New York");
        districtRepository.save(district);

        street.setId(1);
        street.setName("Brooklyn");
        street.setDistrict(district);
        streetRepository.save(street);

        building.setBuildingNumber(1);
        building.setBuildingType("COMMERCIAL_BUILDINGS");
        building.setStreet(street);
        building.setId(1);
        buildingRepository.save(building);

        address.setId(1L);
        address.setHomeNumber("1");
        address.setHomeCode(10102);
        address.setOwnerCardNumber("11111112");
        address.setBuilding(building);
        addressRepository.save(address);
    }

    @AfterEach
    public void tearDown() {
        addressRepository.delete(address);
    }

    @Test
    void findById() {
        Long id = address.getAddressId();
        Optional<Address> optionalAddress = addressRepository.findById(id);
        optionalAddress.ifPresent(value -> assertEquals(value, address));
    }
    @Test
    void findAll(){
        assertEquals(1, addressRepository.findAll().size());
    }
}
