package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.District;
import com.epam.recreation_module.model.Street;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StreetRepositoryTest {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private StreetRepository streetRepository;

    Street street = new Street();

    @BeforeEach
    public void setUp() {

        District district = new District();
        district.setId(1);
        district.setName("New York");
        districtRepository.save(district);
        street.setStreetId(1);
        street.setId(1);
        street.setName("Brooklyn");
        street.setDistrict(district);
        streetRepository.save(street);

    }

    @AfterEach
    public void tearDown() {
        districtRepository.deleteAll();
        streetRepository.deleteAll();
    }

    @Test
    void findById() {
        Integer id = street.getStreetId();
        Optional<Street> optionalStreet = streetRepository.findById(id);
        optionalStreet.ifPresent(value -> assertEquals(value, street));
    }

    @Test
    void findAll() {
        assertEquals(1, streetRepository.findAll().size());
    }
}