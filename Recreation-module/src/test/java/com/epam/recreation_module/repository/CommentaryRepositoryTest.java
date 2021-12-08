package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class CommentaryRepositoryTest {

    @Autowired
    private CommentaryRepository commentaryRepository;
    @Autowired
    private RecreationRepository recreationRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private AddressRepository addressRepository;

    Commentary commentary = new Commentary();
    Recreation recreation = new Recreation();

    @BeforeEach
    public void setUp() {
        District district = new District();
        Street street = new Street();
        Building building = new Building();
        Address address = new Address();

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


        recreation.setName("Disney");
        recreation.setRecreationStatus(RecreationStatus.OPEN);
        recreation.setRecreationCategory(RecreationCategory.PARK);
        recreation.setDescription("For all family members");
        recreation.setAddress(address);
        recreation.setAvailableSits(100);
        recreation.setExist(true);
        recreation.setPrice(10.5);
        recreation.setOpeningTime(LocalDateTime.now().minusDays(1));
        recreation.setClosingTime(LocalDateTime.now().plusDays(1));
        recreationRepository.save(recreation);

        commentary.setWriterId(1L);
        commentary.setWrittenTime(LocalDateTime.now());
        commentary.setCommentText("This is a commentary");
        commentary.setWriter("Jhon Adams");
        commentary.setRecreation(recreation);
        commentary.setDeleted(false);
        commentary.setId(1L);
        commentaryRepository.save(commentary);
    }

    @AfterEach
    public void tearDown() {
        commentaryRepository.deleteAll();
    }

    @Test
    void getCommentaries() {
        assertEquals(1, commentaryRepository.findAll().size());
    }

    @Test
    void getCommentById() {
        Long id = commentary.getId();
        Optional<Commentary> optionalCommentary = commentaryRepository.findById(id);
        optionalCommentary.ifPresent(value -> assertEquals(value, this.commentary));
    }

    @Test
    void allCommentaries() {
        Long id = recreation.getId();
        List<Commentary> commentaryList = commentaryRepository.allCommentaries(id);
        assertEquals(1, commentaryList.size());
    }
}

