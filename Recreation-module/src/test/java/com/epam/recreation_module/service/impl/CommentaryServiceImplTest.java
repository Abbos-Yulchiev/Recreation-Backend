package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.repository.CommentaryRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Done
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

    Commentary commentary = new Commentary();
    Commentary commentary1 = new Commentary();
    Recreation recreation = new Recreation();
    Recreation recreation1 = new Recreation();
    User user = new User();

    @BeforeEach
    void setUp() {
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
        address.setHomeNumber("42");
        address.setHomeCode(1);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("42");

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

        commentary.setWrittenTime(LocalDateTime.of(1, 1, 1, 1, 1));
        commentary.setWriter("Writer");
        commentary.setId(123L);
        commentary.setDeleted(false);
        commentary.setRecreation(recreation);
        commentary.setWriterId(123L);
        commentary.setCommentText("Comment Text");

        Street street1 = new Street();
        street1.setId(1);
        street1.setName("Name");
        street1.setDistrict(new District());
        street1.setStreetId(123);

        Building building1 = new Building();
        building1.setBuildingId(123);
        building1.setStreet(street1);
        building1.setId(1);
        building1.setBuildingNumber(10);
        building1.setBuildingType("Building Type");

        Address address1 = new Address();
        address1.setBuilding(building1);
        address1.setHomeNumber("42");
        address1.setHomeCode(1);
        address1.setId(123L);
        address1.setAddressId(123L);
        address1.setOwnerCardNumber("42");

        recreation1.setOpeningTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation1.setRecreationCategory(RecreationCategory.PARK);
        recreation1.setAvailableSits(1);
        recreation1.setPrice(10.0);
        recreation1.setRecreationStatus(RecreationStatus.OPEN);
        recreation1.setAddress(address1);
        recreation1.setId(123L);
        recreation1.setClosingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        recreation1.setName("Name");
        recreation1.setDescription("The characteristics of someone or something");
        recreation1.setExist(true);

        commentary1.setWrittenTime(LocalDateTime.of(1, 1, 1, 1, 1));
        commentary1.setWriter("Writer");
        commentary1.setId(123L);
        commentary1.setDeleted(false);
        commentary1.setRecreation(recreation1);
        commentary1.setWriterId(123L);
        commentary1.setCommentText("Comment Text");

        user.setId(123L);
        user.setLastName("Juliano");
        user.setPassword("12345678");
        user.setFirstName("Jane");
        user.setUsername("username");
        user.setCitizenId("12345678");
        user.setRoles(new HashSet<Role>());
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setDeleted(true);
        user.setEnabled(true);
    }

    @Test
    void testGetCommentaries() {
        Optional<Recreation> ofResult = Optional.of(recreation);
        when(this.recreationRepository.findById(any())).thenReturn(ofResult);
        when(this.commentaryRepository.allCommentaries(any())).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualCommentaries = this.commentaryServiceImpl.getCommentaries(123L);
        assertEquals("<200 OK OK,[],[]>", actualCommentaries.toString());
        assertTrue(actualCommentaries.hasBody());
        assertEquals(HttpStatus.OK, actualCommentaries.getStatusCode());
        assertTrue(actualCommentaries.getHeaders().isEmpty());
        verify(this.recreationRepository).findById(any());
        verify(this.commentaryRepository).allCommentaries(any());
    }

    @Test
    void testGetCommentaries2() {
        when(this.recreationRepository.findById(any())).thenReturn(Optional.empty());
        when(this.commentaryRepository.allCommentaries(any())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> this.commentaryServiceImpl.getCommentaries(123L));
        verify(this.recreationRepository).findById(any());
    }

    @Test
    void testGetCommentById() {
        Optional<Commentary> ofResult = Optional.of(commentary);
        when(this.commentaryRepository.findById(any())).thenReturn(ofResult);
        ResponseEntity<?> actualCommentById = this.commentaryServiceImpl.getCommentById(123L);
        assertEquals("<200 OK OK,Commentary(commentText=Comment Text, recreation=Recreation(name=Name, " +
                        "description=The characteristics of someone or something, availableSits=1, openingTime=0001-01-01T01:01, " +
                        "closingTime=0001-01-01T01:01, recreationCategory=PARK, recreationStatus=OPEN, address=Address(addressId=123, " +
                        "id=123, homeCode=1, homeNumber=42, building=Building(buildingId=123, id=1, street=Street(streetId=123, id=1, name=Name, " +
                        "district=District(districtId=null, id=null, name=null)), buildingNumber=10, buildingType=Building Type), ownerCardNumber=42), " +
                        "exist=true, price=10.0), writtenTime=0001-01-01T01:01, deleted=false, writer=Writer, writerId=123),[]>",
                actualCommentById.toString());
        assertTrue(actualCommentById.hasBody());
        assertTrue(actualCommentById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualCommentById.getStatusCode());
        verify(this.commentaryRepository).findById(any());
    }

    @Test
    void testGetCommentById2() {
        when(this.commentaryRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.commentaryServiceImpl.getCommentById(123L));
        verify(this.commentaryRepository).findById(any());
    }

    @Test
    void testAddCommentary() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(this.recreationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.commentaryServiceImpl.addCommentary(new CommentaryDTO()));
        verify(this.recreationRepository).findById(any());
    }

    @Test
    void testAddCommentary2() {
        ResponseEntity<?> actualAddCommentaryResult = this.commentaryServiceImpl.addCommentary(new CommentaryDTO());
        assertEquals("User not found", actualAddCommentaryResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,User not found,[]>", actualAddCommentaryResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualAddCommentaryResult.getStatusCode());
        assertTrue(actualAddCommentaryResult.getHeaders().isEmpty());
    }

    @Test
    void testEditComment() {
        ResponseEntity<?> actualEditCommentResult = this.commentaryServiceImpl.editComment(123L, new CommentaryDTO());
        assertEquals("User not found", actualEditCommentResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,User not found,[]>", actualEditCommentResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualEditCommentResult.getStatusCode());
        assertTrue(actualEditCommentResult.getHeaders().isEmpty());
    }

    @Test
    void testEditMyComment() {
        ResponseEntity<?> actualEditMyCommentResult = this.commentaryServiceImpl.editMyComment(123L, new CommentaryDTO());
        assertEquals("User not found", actualEditMyCommentResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,User not found,[]>", actualEditMyCommentResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualEditMyCommentResult.getStatusCode());
        assertTrue(actualEditMyCommentResult.getHeaders().isEmpty());
    }

    @Test
    void testDeleteComment() {
        when(this.commentaryRepository.save(any())).thenReturn(commentary);
        when(this.commentaryRepository.findByIdAndDeletedIsFalse(any())).thenReturn(commentary);
        ResponseEntity<?> actualDeleteCommentResult = this.commentaryServiceImpl.deleteComment(123L);
        assertEquals("Comment deleted! Id: 123", actualDeleteCommentResult.getBody());
        assertEquals("<200 OK OK,Comment deleted! Id: 123,[]>", actualDeleteCommentResult.toString());
        assertEquals(HttpStatus.OK, actualDeleteCommentResult.getStatusCode());
        assertTrue(actualDeleteCommentResult.getHeaders().isEmpty());
        verify(this.commentaryRepository).findByIdAndDeletedIsFalse(any());
        verify(this.commentaryRepository).save(any());
    }

    @Test
    void testDeleteComment2() {
        commentary.setDeleted(true);
        when(this.commentaryRepository.findByIdAndDeletedIsFalse(any())).thenReturn(commentary);
        ResponseEntity<?> actualDeleteCommentResult = this.commentaryServiceImpl.deleteComment(123L);
        assertEquals("Comment Already deleted!", actualDeleteCommentResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Comment Already deleted!,[]>", actualDeleteCommentResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualDeleteCommentResult.getStatusCode());
        assertTrue(actualDeleteCommentResult.getHeaders().isEmpty());
        verify(this.commentaryRepository).findByIdAndDeletedIsFalse(any());
    }

    @Test
    void testDeleteComment3() {
        when(this.commentaryRepository.findByIdAndDeletedIsFalse(any())).thenThrow(new NullPointerException("foo"));
        ResponseEntity<?> actualDeleteCommentResult = this.commentaryServiceImpl.deleteComment(123L);
        assertEquals("User not found", actualDeleteCommentResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,User not found,[]>", actualDeleteCommentResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualDeleteCommentResult.getStatusCode());
        assertTrue(actualDeleteCommentResult.getHeaders().isEmpty());
        verify(this.commentaryRepository).findByIdAndDeletedIsFalse(any());
    }

    @Test
    void testDeleteMyComment() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        ResponseEntity<?> actualDeleteMyCommentResult = this.commentaryServiceImpl.deleteMyComment(123L);
        assertEquals("User not found", actualDeleteMyCommentResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,User not found,[]>", actualDeleteMyCommentResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualDeleteMyCommentResult.getStatusCode());
        assertTrue(actualDeleteMyCommentResult.getHeaders().isEmpty());
    }

    @Test
    void testDeleteMyComment2() {
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
        address.setHomeNumber("42");
        address.setHomeCode(1);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("42");

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

        Commentary commentary = new Commentary();
        commentary.setWrittenTime(LocalDateTime.of(1, 1, 1, 1, 1));
        commentary.setWriter("Writer");
        commentary.setId(123L);
        commentary.setDeleted(true);
        commentary.setRecreation(recreation);
        commentary.setWriterId(123L);
        commentary.setCommentText("Comment Text");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(this.commentaryRepository.findByIdAndDeletedIsFalse((Long) any())).thenReturn(commentary);
        ResponseEntity<?> actualDeleteMyCommentResult = this.commentaryServiceImpl.deleteMyComment(123L);
        assertEquals("<409 CONFLICT Conflict,This is not your comment you can not delete!,[]>", actualDeleteMyCommentResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualDeleteMyCommentResult.getStatusCode());
        assertTrue(actualDeleteMyCommentResult.getHeaders().isEmpty());
        verify(this.commentaryRepository).findByIdAndDeletedIsFalse(any());
    }
}

