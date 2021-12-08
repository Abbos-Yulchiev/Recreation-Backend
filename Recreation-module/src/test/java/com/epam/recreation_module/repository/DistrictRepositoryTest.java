package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Done
 */
@ContextConfiguration(classes = {DistrictRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.epam.recreation_module.model"})
@DataJpaTest
class DistrictRepositoryTest {
    
    @Autowired
    private DistrictRepository districtRepository;

    @Test
    void testFindAll() {
        District district = new District();
        district.setId(1);
        district.setName("California");

        District district1 = new District();
        district1.setId(1);
        district1.setName("Ohio");
        this.districtRepository.save(district);
        this.districtRepository.save(district1);
        assertEquals(2, this.districtRepository.findAll().size());
    }

    @Test
    void testFindById() {
        District district = new District();
        district.setId(1);
        district.setName("Los Angles");

        District district1 = new District();
        district1.setId(1);
        district1.setName("New York");
        this.districtRepository.save(district);
        this.districtRepository.save(district1);

        District district2 = new District();
        district2.setId(1);
        district2.setName("Brooklyn");
        assertTrue(this.districtRepository.findById(this.districtRepository.save(district2).getDistrictId())
                .isPresent());
    }
}

