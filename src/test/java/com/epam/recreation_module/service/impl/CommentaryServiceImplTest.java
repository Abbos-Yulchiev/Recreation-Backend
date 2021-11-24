package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.CommentaryRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Done Checked
 *
 * @author Yulchiev Abbos
 */

@ContextConfiguration(classes = {CommentaryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentaryServiceImplTest {

    @MockBean
    private CommentaryRepository commentaryRepository;

    @Autowired
    private CommentaryServiceImpl commentaryServiceImpl;

    @MockBean
    private RecreationRepository recreationRepository;

    @Test
    void testAddCommentary() throws NotFoundException {
        ApiResponse actualAddCommentaryResult = this.commentaryServiceImpl.addCommentary(new CommentaryDTO());
        assertEquals("User not found!", actualAddCommentaryResult.getMessage());
        assertFalse(actualAddCommentaryResult.isSuccess());
    }

    @Test
    void testEditComment() {
        ApiResponse actualEditCommentResult = this.commentaryServiceImpl.editComment(123L, new CommentaryDTO());
        assertEquals("User not found", actualEditCommentResult.getMessage());
        assertFalse(actualEditCommentResult.isSuccess());
    }

    @Test
    void testEditMyComment() {
        ApiResponse actualEditMyCommentResult = this.commentaryServiceImpl.editMyComment(123L, new CommentaryDTO());
        assertEquals("User not found", actualEditMyCommentResult.getMessage());
        assertFalse(actualEditMyCommentResult.isSuccess());
    }

    @Test
    void testGetCommentaries() {
        Street street = new Street();
        street.setId(1);
        street.setName("Name");
        street.setDistrict(new District());
        street.setStreetId(123);

        Building building = new Building();
        building.setBuildingId(123);
        building.setStreet(street);
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");

        Address address = new Address();
        address.setBuilding(building);
        address.setHomeNumber("1");
        address.setHomeCode(1);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("1");

        Recreation recreation = new Recreation();
        recreation.setOpeningTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation.setRecreationCategory(RecreationCategory.PARK);
        recreation.setAvailableSits(1);
        recreation.setPrice(10.0);
        recreation.setRecreationStatus(RecreationStatus.OPEN);
        recreation.setAddress(address);
        recreation.setId(123L);
        recreation.setClosingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation.setName("Name");
        recreation.setDescription("The characteristics of someone or something");
        recreation.setExist(true);
        Optional<Recreation> ofResult = Optional.<Recreation>of(recreation);
        when(this.recreationRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
        when(this.commentaryRepository.allCommentaries((Long) ArgumentMatchers.any())).thenReturn(new ArrayList<Object>());
        ResponseEntity<?> actualCommentaries = this.commentaryServiceImpl.getCommentaries(123L);
        assertEquals("<200 OK OK,[],[]>", actualCommentaries.toString());
        assertTrue(actualCommentaries.hasBody());
        assertEquals(HttpStatus.OK, actualCommentaries.getStatusCode());
        assertTrue(actualCommentaries.getHeaders().isEmpty());
        verify(this.recreationRepository).findById((Long) ArgumentMatchers.any());
        verify(this.commentaryRepository).allCommentaries((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetCommentById() {
        Building building = new Building();
        building.setBuildingId(1);
        building.setStreet(new Street());
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");

        Address address = new Address();
        address.setBuilding(building);
        address.setHomeNumber("1");
        address.setHomeCode(1);
        address.setId(1L);
        address.setAddressId(1L);
        address.setOwnerCardNumber("11111112");

        Recreation recreation = new Recreation();
        recreation.setOpeningTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation.setRecreationCategory(RecreationCategory.PARK);
        recreation.setAvailableSits(1);
        recreation.setPrice(10.0);
        recreation.setRecreationStatus(RecreationStatus.OPEN);
        recreation.setAddress(address);
        recreation.setId(123L);
        recreation.setClosingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation.setName("Name");
        recreation.setDescription("The characteristics of someone or something");
        recreation.setExist(true);

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

        User user1 = new User();
        user1.setLastName("Lastname");
        user1.setPassword("123");
        user1.setAccountNonLocked(true);
        user1.setAccountNonExpired(true);
        user1.setUsername("username");
        user1.setCredentialsNonExpired(true);
        user1.setCitizenId("12345678");
        user1.setId(123L);
        user1.setDeleted(true);
        user1.setEnabled(true);
        user1.setFirstName("Firstname");
        user1.setRoles(new HashSet<Role>());

        Commentary commentary = new Commentary();
        commentary.setUpdatedAt(mock(Timestamp.class));
        commentary.setId(123L);
        commentary.setCreatedAt(mock(Timestamp.class));
        commentary.setDeleted(true);
        commentary.setRecreation(recreation);
        commentary.setCreatedBy(user);
        commentary.setUpdatedBy(user1);
        commentary.setCommentText("Comment Text");
        Optional<Commentary> ofResult = Optional.<Commentary>of(commentary);
        when(this.commentaryRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
        ApiResponse actualCommentById = this.commentaryServiceImpl.getCommentById(123L);
        assertEquals("Ok!", actualCommentById.getMessage());
        assertEquals("ApiResponse(message=Ok!, success=true, roles=null, object=Commentary(commentText=Comment Text,"
                        + " recreation=Recreation(name=Name, description=The characteristics of someone or something, availableSits=1,"
                        + " openingTime=0001-01-01T01:01, closingTime=0001-01-01T01:01, recreationCategory=PARK, recreationStatus=OPEN,"
                        + " address=Address(addressId=1, id=1, homeCode=1, homeNumber=1, building=Building(buildingId=1,"
                        + " id=1, street=Street(streetId=null, id=null, name=null, district=null), buildingNumber=10, buildingType"
                        + "=Building Type), ownerCardNumber=11111112), exist=true, price=10.0), deleted=true))",
                actualCommentById.toString());
        assertTrue(actualCommentById.isSuccess());
        verify(this.commentaryRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testDeleteComment() {
        Street street = new Street();
        street.setId(1);
        street.setName("Street name");
        street.setDistrict(new District());
        street.setStreetId(123);

        Building building = new Building();
        building.setBuildingId(123);
        building.setStreet(street);
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");

        Address address = new Address();
        address.setBuilding(building);
        address.setHomeNumber("1");
        address.setHomeCode(1);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("11111112");

        Recreation recreation = new Recreation();
        recreation.setOpeningTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation.setRecreationCategory(RecreationCategory.PARK);
        recreation.setAvailableSits(1);
        recreation.setPrice(10.0);
        recreation.setRecreationStatus(RecreationStatus.OPEN);
        recreation.setAddress(address);
        recreation.setId(123L);
        recreation.setClosingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation.setName("Recreation");
        recreation.setDescription("The characteristics of someone or something");
        recreation.setExist(true);

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
        user.setFirstName("FIrstname");
        user.setRoles(new HashSet<Role>());

        User user1 = new User();
        user1.setLastName("Lastname");
        user1.setPassword("123");
        user1.setAccountNonLocked(true);
        user1.setAccountNonExpired(true);
        user1.setUsername("username");
        user1.setCredentialsNonExpired(true);
        user1.setCitizenId("12345678");
        user1.setId(123L);
        user1.setDeleted(true);
        user1.setEnabled(true);
        user1.setFirstName("Firstname");
        user1.setRoles(new HashSet<Role>());

        Commentary commentary = new Commentary();
        commentary.setUpdatedAt(mock(Timestamp.class));
        commentary.setId(123L);
        commentary.setCreatedAt(mock(Timestamp.class));
        commentary.setDeleted(true);
        commentary.setRecreation(recreation);
        commentary.setCreatedBy(user);
        commentary.setUpdatedBy(user1);
        commentary.setCommentText("Comment Text");
        when(this.commentaryRepository.findByIdAndDeletedIsFalse((Long) ArgumentMatchers.any())).thenReturn(commentary);
        ApiResponse actualDeleteCommentResult = this.commentaryServiceImpl.deleteComment(123L);
        assertEquals("Comment Already deleted!", actualDeleteCommentResult.getMessage());
        assertFalse(actualDeleteCommentResult.isSuccess());
        verify(this.commentaryRepository).findByIdAndDeletedIsFalse((Long) ArgumentMatchers.any());
    }

    @Test
    void testDeleteMyComment() {
        ApiResponse actualDeleteMyCommentResult = this.commentaryServiceImpl.deleteMyComment(123L);
        assertEquals("Invalid User!", actualDeleteMyCommentResult.getMessage());
        assertFalse(actualDeleteMyCommentResult.isSuccess());
    }
}

