package com.epam.recreation_module.repository;

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
class BuildingRepositoryTest {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    Building building = new Building();
    District district = new District();
    Street street = new Street();

    @BeforeEach
    void setUp() {
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
    }

    @AfterEach
    void tearDown() {
        districtRepository.deleteAll();
        streetRepository.deleteAll();
        buildingRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        assertEquals(1, this.buildingRepository.findAll().size());
    }

    @Test
    void testFindById() {
        Integer id = building.getBuildingId();
        Optional<Building> optionalBuilding = buildingRepository.findById(id);
        optionalBuilding.ifPresent(value -> assertEquals(value, building));
    }
}