package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.security.hmac.HMACUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecreationServiceImplTest {

    @Test
    void testGetRecreationById() {
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
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByIdAndExistTrue((Long) ArgumentMatchers.any())).thenReturn(Optional.<Recreation>of(recreation));
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        ResponseEntity<?> actualRecreationById = (new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null)))))
                .getRecreationById(123L);
        assertEquals(
                "<200 OK OK,Recreation(name=Name, description=The characteristics of someone or something, availableSits=1,"
                        + " openingTime=0001-01-01T01:01, closingTime=0001-01-01T01:01, recreationCategory=PARK, recreationStatus=OPEN,"
                        + " address=Address(addressId=123, id=123, homeCode=1, homeNumber=42, building=Building(buildingId=123,"
                        + " id=1, street=Street(streetId=123, id=1, name=Name, district=District(districtId=null, id=null,"
                        + " name=null)), buildingNumber=10, buildingType=Building Type), ownerCardNumber=42), exist=true,"
                        + " price=10.0),[]>",
                actualRecreationById.toString());
        assertTrue(actualRecreationById.hasBody());
        assertTrue(actualRecreationById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualRecreationById.getStatusCode());
        verify(recreationRepository).findByIdAndExistTrue((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetRecreationById2() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByIdAndExistTrue((Long) ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        ResponseEntity<?> actualRecreationById = (new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null)))))
                .getRecreationById(123L);
        assertEquals("Invalid recreation given! Recreation not found!", actualRecreationById.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Invalid recreation given! Recreation not found!,[]>",
                actualRecreationById.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualRecreationById.getStatusCode());
        assertTrue(actualRecreationById.getHeaders().isEmpty());
        verify(recreationRepository).findByIdAndExistTrue((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetAll() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<Recreation>(new ArrayList<Recreation>()));
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        ResponseEntity<?> actualAll = (new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository,
                buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null))))).getAll(1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAll.toString());
        assertTrue(((PageImpl) actualAll.getBody()).toList().isEmpty());
        assertTrue(actualAll.hasBody());
        assertEquals(HttpStatus.OK, actualAll.getStatusCode());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(recreationRepository).findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testGetByCategory() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByCategory((String) ArgumentMatchers.any(), (org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<Recreation>(new ArrayList<Recreation>()));
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        ResponseEntity<?> actualByCategory = (new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null)))))
                .getByCategory("Category", 1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualByCategory.toString());
        assertTrue(((PageImpl) actualByCategory.getBody()).toList().isEmpty());
        assertTrue(actualByCategory.hasBody());
        assertEquals(HttpStatus.OK, actualByCategory.getStatusCode());
        assertTrue(actualByCategory.getHeaders().isEmpty());
        verify(recreationRepository).findByCategory((String) ArgumentMatchers.any(), (org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testGetAllByExist() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findAllByExistTrue((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<Recreation>(new ArrayList<Recreation>()));
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        ResponseEntity<?> actualAllByExist = (new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null))))).getAllByExist(1,
                3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAllByExist.toString());
        assertTrue(((PageImpl) actualAllByExist.getBody()).toList().isEmpty());
        assertTrue(actualAllByExist.hasBody());
        assertEquals(HttpStatus.OK, actualAllByExist.getStatusCode());
        assertTrue(actualAllByExist.getHeaders().isEmpty());
        verify(recreationRepository).findAllByExistTrue((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testCreateRecreation() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.existsByNameAndAddress_Id((String) ArgumentMatchers.any(), (Long) ArgumentMatchers.any())).thenReturn(true);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        RecreationServiceImpl recreationServiceImpl = new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null))));
        Address address = new Address();
        Address address1 = new Address();
        ResponseEntity<?> actualCreateRecreationResult = recreationServiceImpl
                .createRecreation(new RecreationDTO("Name", "The characteristics of someone or something", 1,
                        new String[]{"foo", "foo", "foo"}, new String[]{"foo", "foo", "foo"}, "Recreation Category",
                        "Recreation Status", 10.0, new Address[]{address, address1, new Address()}));
        assertEquals("<409 CONFLICT Conflict,ApiResponse(message=This recreation already exist!, success=false, roles=null,"
                + " object=null),[]>", actualCreateRecreationResult.toString());
        assertTrue(actualCreateRecreationResult.hasBody());
        assertTrue(actualCreateRecreationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.CONFLICT, actualCreateRecreationResult.getStatusCode());
        assertEquals("This recreation already exist!", ((ApiResponse) actualCreateRecreationResult.getBody()).getMessage());
        assertFalse(((ApiResponse) actualCreateRecreationResult.getBody()).isSuccess());
        verify(recreationRepository).existsByNameAndAddress_Id((String) ArgumentMatchers.any(), (Long) ArgumentMatchers.any());
    }

    @Test
    void testCreateRecreation2() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.existsByNameAndAddress_Id((String) ArgumentMatchers.any(), (Long) ArgumentMatchers.any())).thenReturn(true);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        RecreationServiceImpl recreationServiceImpl = new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null))));

        District district = new District();
        district.setId(1);
        district.setName("Name");
        district.setDistrictId(123);

        Street street = new Street();
        street.setId(1);
        street.setName("Name");
        street.setDistrict(district);
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
        address.setOwnerCardNumber("1");
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getName()).thenReturn("Name");
        when(recreationDTO.getAddress()).thenReturn(new Address[]{address});
        ResponseEntity<?> actualCreateRecreationResult = recreationServiceImpl.createRecreation(recreationDTO);
        assertEquals("<409 CONFLICT Conflict,ApiResponse(message=This recreation already exist!, success=false, roles=null,"
                + " object=null),[]>", actualCreateRecreationResult.toString());
        assertTrue(actualCreateRecreationResult.hasBody());
        assertTrue(actualCreateRecreationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.CONFLICT, actualCreateRecreationResult.getStatusCode());
        assertEquals("This recreation already exist!", ((ApiResponse) actualCreateRecreationResult.getBody()).getMessage());
        assertFalse(((ApiResponse) actualCreateRecreationResult.getBody()).isSuccess());
        verify(recreationRepository).existsByNameAndAddress_Id((String) ArgumentMatchers.any(), (Long) ArgumentMatchers.any());
        verify(recreationDTO).getAddress();
        verify(recreationDTO).getName();
    }

    @Test
    void testEditRecreation() {

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
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Recreation>of(recreation));
        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.findById((Long) ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        RestTemplate restTemplate = mock(RestTemplate.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        RecreationServiceImpl recreationServiceImpl = new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null))));
        Address address1 = new Address();
        Address address2 = new Address();
        ResponseEntity<?> actualEditRecreationResult = recreationServiceImpl.editRecreation(123L,
                new RecreationDTO("Name", "The characteristics of someone or something", 1, new String[]{"foo", "foo", "foo"},
                        new String[]{"foo", "foo", "foo"}, "Recreation Category", "Recreation Status", 10.0,
                        new Address[]{address1, address2, new Address()}));
        assertEquals("Address not found! Invalid Address given!\nAddress: \n\tnull\nHome number: null\nHome code: null",
                actualEditRecreationResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Address not found! Invalid Address given!\n" + "Address: \n" + "\tnull\n"
                + "Home number: null\n" + "Home code: null,[]>", actualEditRecreationResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualEditRecreationResult.getStatusCode());
        assertTrue(actualEditRecreationResult.getHeaders().isEmpty());
        verify(recreationRepository).findById((Long) ArgumentMatchers.any());
        verify(addressRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testEditRecreationStatus() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<Recreation>empty());
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        assertThrows(NotFoundException.class,
                () -> (new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                        streetRepository, districtRepository,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                        streetRepository1, districtRepository1, new ExternalConnections(null, null, null)))))
                        .editRecreationStatus(123L, "Status"));
        verify(recreationRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testDeleteRecreationById() {
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
        Optional<Recreation> ofResult = Optional.<Recreation>of(recreation);

        District district = new District();
        district.setId(1);
        district.setName("Name");
        district.setDistrictId(123);

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.save((Recreation) ArgumentMatchers.any())).thenReturn(recreation);
        when(recreationRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        ResponseEntity<?> actualDeleteRecreationByIdResult = (new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null)))))
                .deleteRecreationById(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Recreation deleted., success=true, roles=null, object=null),[]>",
                actualDeleteRecreationByIdResult.toString());
        assertTrue(actualDeleteRecreationByIdResult.hasBody());
        assertTrue(actualDeleteRecreationByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteRecreationByIdResult.getStatusCode());
        assertEquals("Recreation deleted.", ((ApiResponse) actualDeleteRecreationByIdResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualDeleteRecreationByIdResult.getBody()).isSuccess());
        verify(recreationRepository).findById((Long) ArgumentMatchers.any());
        verify(recreationRepository).save((Recreation) ArgumentMatchers.any());
    }

    @Test
    void testSaveNewRecreationAddress() {
        District district = new District();
        district.setId(1);
        district.setName("Name");
        district.setDistrictId(123);

        Street street = new Street();
        street.setId(1);
        street.setName("Name");
        street.setDistrict(district);
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
        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.save((Address) ArgumentMatchers.any())).thenReturn(address);

        District district1 = new District();
        district1.setId(1);
        district1.setName("Name");
        district1.setDistrictId(123);

        Street street1 = new Street();
        street1.setId(1);
        street1.setName("Name");
        street1.setDistrict(district1);
        street1.setStreetId(123);

        Building building1 = new Building();
        building1.setBuildingId(123);
        building1.setStreet(street1);
        building1.setId(1);
        building1.setBuildingNumber(10);
        building1.setBuildingType("Building Type");
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        when(buildingRepository.save((Building) ArgumentMatchers.any())).thenReturn(building);

        StreetRepository streetRepository = mock(StreetRepository.class);
        when(streetRepository.save((Street) ArgumentMatchers.any())).thenReturn(street);

        District district3 = new District();
        district3.setId(1);
        district3.setName("Name");
        district3.setDistrictId(123);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        when(districtRepository.save((District) ArgumentMatchers.any())).thenReturn(district3);
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository1 = mock(RecreationRepository.class);
        RestTemplate restTemplate1 = mock(RestTemplate.class);
        AddressRepository addressRepository1 = mock(AddressRepository.class);
        BuildingRepository buildingRepository1 = mock(BuildingRepository.class);
        StreetRepository streetRepository1 = mock(StreetRepository.class);
        DistrictRepository districtRepository1 = mock(DistrictRepository.class);
        RecreationServiceImpl recreationServiceImpl = new RecreationServiceImpl(recreationRepository, restTemplate,
                addressRepository, buildingRepository, streetRepository, districtRepository,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository1, restTemplate1, addressRepository1, buildingRepository1,
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null))));

        District district4 = new District();
        district4.setId(1);
        district4.setName("Name");
        district4.setDistrictId(123);

        Street street3 = new Street();
        street3.setId(1);
        street3.setName("Name");
        street3.setDistrict(district4);
        street3.setStreetId(123);

        Building building2 = new Building();
        building2.setBuildingId(123);
        building2.setStreet(street3);
        building2.setId(1);
        building2.setBuildingNumber(10);
        building2.setBuildingType("Building Type");

        Address address1 = new Address();
        address1.setBuilding(building2);
        address1.setHomeNumber("42");
        address1.setHomeCode(1);
        address1.setId(123L);
        address1.setAddressId(123L);
        address1.setOwnerCardNumber("42");
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getAddress()).thenReturn(new Address[]{address1});
        assertSame(address, recreationServiceImpl.saveNewRecreationAddress(recreationDTO));
        verify(addressRepository).save((Address) ArgumentMatchers.any());
        verify(buildingRepository).save((Building) ArgumentMatchers.any());
        verify(streetRepository).save((Street) ArgumentMatchers.any());
        verify(districtRepository).save((District) ArgumentMatchers.any());
        verify(recreationDTO).getAddress();
    }
}

//TODO:saveNewRecreationAddress() check;

