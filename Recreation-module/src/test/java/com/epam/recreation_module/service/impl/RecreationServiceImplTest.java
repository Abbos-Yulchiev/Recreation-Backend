package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.model.enums.RecreationCategory;
import com.epam.recreation_module.model.enums.RecreationStatus;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.security.hmac.HMACUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Done
 */
class RecreationServiceImplTest {

    private static final District district = new District();
    private static final Street street = new Street();
    private static final Building building = new Building();
    private static final Address address = new Address();
    Recreation recreation = new Recreation();

    @BeforeEach
    void setUp() {
        district.setId(1);
        district.setName("Name");
        district.setDistrictId(123);

        street.setId(1);
        street.setName("Name");
        street.setDistrict(district);
        street.setStreetId(123);

        building.setBuildingId(123);
        building.setStreet(street);
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");

        address.setBuilding(building);
        address.setHomeNumber("42");
        address.setHomeCode(0);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("42");

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
    }

    @Test
    void testGetRecreationById() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByIdAndExistTrue(any())).thenReturn(Optional.of(recreation));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                .getRecreationById(123L);
        assertEquals(
                "<200 OK OK,Recreation(name=Name, description=The characteristics of someone or something, availableSits=1, " +
                        "openingTime=0001-01-01T01:01, closingTime=0001-01-01T01:01, recreationCategory=PARK, recreationStatus=OPEN, " +
                        "address=Address(addressId=123, id=123, homeCode=0, homeNumber=42, building=Building(buildingId=123, id=1, " +
                        "street=Street(streetId=123, id=1, name=Name, district=District(districtId=123, id=1, name=Name)), buildingNumber=10, " +
                        "buildingType=Building Type), ownerCardNumber=42), exist=true, price=10.0),[]>",
                actualRecreationById.toString());
        assertTrue(actualRecreationById.hasBody());
        assertTrue(actualRecreationById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualRecreationById.getStatusCode());
        verify(recreationRepository).findByIdAndExistTrue(any());
    }

    @Test
    void testGetRecreationById2() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByIdAndExistTrue(any())).thenThrow(new NotFoundException("An error occurred"));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                .getRecreationById(123L);
        assertEquals("Invalid recreation given! Recreation not found!", actualRecreationById.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Invalid recreation given! Recreation not found!,[]>",
                actualRecreationById.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualRecreationById.getStatusCode());
        assertTrue(actualRecreationById.getHeaders().isEmpty());
        verify(recreationRepository).findByIdAndExistTrue(any());
    }

    @Test
    void testGetRecreationById3() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByIdAndExistTrue(any())).thenReturn(Optional.empty());
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                .getRecreationById(123L);
        assertEquals("Invalid recreation given! Recreation not found!", actualRecreationById.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Invalid recreation given! Recreation not found!,[]>",
                actualRecreationById.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualRecreationById.getStatusCode());
        assertTrue(actualRecreationById.getHeaders().isEmpty());
        verify(recreationRepository).findByIdAndExistTrue(any());
    }

    @Test
    void testGetAll() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).getAll(1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAll.toString());
        assertTrue(((PageImpl) Objects.requireNonNull(actualAll.getBody())).toList().isEmpty());
        assertTrue(actualAll.hasBody());
        assertEquals(HttpStatus.OK, actualAll.getStatusCode());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(recreationRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetByCategory() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findByCategory( any(), any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                .getByCategory("Category", 1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualByCategory.toString());
        assertTrue(((PageImpl) Objects.requireNonNull(actualByCategory.getBody())).toList().isEmpty());
        assertTrue(actualByCategory.hasBody());
        assertEquals(HttpStatus.OK, actualByCategory.getStatusCode());
        assertTrue(actualByCategory.getHeaders().isEmpty());
        verify(recreationRepository).findByCategory( any(), any());
    }

    @Test
    void testGetAllByExist() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findAllByExistTrue( any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).getAllByExist(1,
                3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAllByExist.toString());
        assertTrue(((PageImpl) Objects.requireNonNull(actualAllByExist.getBody())).toList().isEmpty());
        assertTrue(actualAllByExist.hasBody());
        assertEquals(HttpStatus.OK, actualAllByExist.getStatusCode());
        assertTrue(actualAllByExist.getHeaders().isEmpty());
        verify(recreationRepository).findAllByExistTrue( any());
    }

    @Test
    void testCreateRecreation() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.existsByNameAndAddress_Id( any(), any())).thenReturn(true);
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address);
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getName()).thenReturn("Name");
        when(recreationDTO.getAddress()).thenReturn(addressList);
        ResponseEntity<?> actualCreateRecreationResult = recreationServiceImpl.createRecreation(recreationDTO);
        assertEquals("<409 CONFLICT Conflict,ApiResponse(message=This recreation already exist!, success=false, roles=null,"
                + " object=null),[]>", actualCreateRecreationResult.toString());
        assertTrue(actualCreateRecreationResult.hasBody());
        assertTrue(actualCreateRecreationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.CONFLICT, actualCreateRecreationResult.getStatusCode());
        assertEquals("This recreation already exist!", ((ApiResponse) Objects.requireNonNull(actualCreateRecreationResult.getBody())).getMessage());
        assertFalse(((ApiResponse) actualCreateRecreationResult.getBody()).isSuccess());
        verify(recreationRepository).existsByNameAndAddress_Id( any(), any());
        verify(recreationDTO).getAddress();
        verify(recreationDTO).getName();
    }

    /*@Test
    void testCreateRecreation2() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.existsByNameAndAddress_Id( any(), any())).thenReturn(false);
        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.save(any())).thenReturn(address);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        when(buildingRepository.save(any())).thenReturn(building);
        StreetRepository streetRepository = mock(StreetRepository.class);
        when(streetRepository.save(any())).thenReturn(street);

        DistrictRepository districtRepository = mock(DistrictRepository.class);
        when(districtRepository.save(any())).thenReturn(district);
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

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address);
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getOpeningTime()).thenThrow(new NotFoundException("An error occurred"));
        when(recreationDTO.getName()).thenReturn("Name");
        when(recreationDTO.getAddress()).thenReturn(addressList);
        ResponseEntity<?> actualCreateRecreationResult = recreationServiceImpl.createRecreation(recreationDTO);
        assertEquals("Address not found! Invalid Address given!\nAddress: \n\t123\nHome number: 42\nHome code: 0",
                actualCreateRecreationResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Address not found! Invalid Address given!\n" + "Address: \n" + "\t123\n"
                + "Home number: 42\n" + "Home code: 0,[]>", actualCreateRecreationResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualCreateRecreationResult.getStatusCode());
        assertTrue(actualCreateRecreationResult.getHeaders().isEmpty());
        verify(recreationRepository).existsByNameAndAddress_Id( any(), any());
        verify(addressRepository).save(any());
        verify(buildingRepository).save(any());
        verify(streetRepository).save(any());
        verify(districtRepository).save(any());
        verify(recreationDTO, atLeast(1)).getAddress();
        verify(recreationDTO).getName();
        verify(recreationDTO).getOpeningTime();
    }*/

    @Test
    void testEditRecreation() {

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById(any())).thenReturn(Optional.of(recreation));

        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.findById(any())).thenReturn(Optional.of(address));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address);
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getOpeningTime()).thenThrow(new NotFoundException("An error occurred"));
        when(recreationDTO.getAddress()).thenReturn(addressList);
        ResponseEntity<?> actualEditRecreationResult = recreationServiceImpl.editRecreation(123L, recreationDTO);
        assertEquals("Address not found! Invalid Address given!\nAddress: \n\t123\nHome number: 42\nHome code: 0",
                actualEditRecreationResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Address not found! Invalid Address given!\n" + "Address: \n" + "\t123\n"
                + "Home number: 42\n" + "Home code: 0,[]>", actualEditRecreationResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualEditRecreationResult.getStatusCode());
        assertTrue(actualEditRecreationResult.getHeaders().isEmpty());
        verify(recreationRepository).findById(any());
        verify(addressRepository).findById(any());
        verify(recreationDTO).getAddress();
        verify(recreationDTO).getOpeningTime();
    }

    @Test
    void testEditRecreation2() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById(any())).thenReturn(Optional.empty());

        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.findById(any())).thenReturn(Optional.of(address));
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));

        ArrayList<Address> addressList = new ArrayList< >();
        addressList.add(address);
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getOpeningTime()).thenReturn(new ArrayList< >());
        when(recreationDTO.getAddress()).thenReturn(addressList);
        ResponseEntity<?> actualEditRecreationResult = recreationServiceImpl.editRecreation(123L, recreationDTO);
        assertEquals("Address not found! Invalid Address given!\nAddress: \n\t123\nHome number: 42\nHome code: 0",
                actualEditRecreationResult.getBody());
        assertEquals("<404 NOT_FOUND Not Found,Address not found! Invalid Address given!\n" + "Address: \n" + "\t123\n"
                + "Home number: 42\n" + "Home code: 0,[]>", actualEditRecreationResult.toString());
        assertEquals(HttpStatus.NOT_FOUND, actualEditRecreationResult.getStatusCode());
        assertTrue(actualEditRecreationResult.getHeaders().isEmpty());
        verify(recreationRepository).findById(any());
        verify(recreationDTO).getAddress();
    }

    @Test
    void testEditRecreationStatus() {
        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.findById(any())).thenReturn(Optional. empty());
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                        streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                        .editRecreationStatus(123L, "Status"));
        verify(recreationRepository).findById(any());
    }

    @Test
    void testDeleteRecreationById() {

        Optional<Recreation> ofResult = Optional. of(recreation);

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        when(recreationRepository.save(any())).thenReturn(recreation);
        when(recreationRepository.findById(any())).thenReturn(ofResult);
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
                new ExternalConnections(null, null, null, userService1, recreationService1));

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
                                streetRepository1, districtRepository1, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                .deleteRecreationById(123L);
        assertEquals("<200 OK OK,ApiResponse(message=Recreation deleted., success=true, roles=null, object=null),[]>",
                actualDeleteRecreationByIdResult.toString());
        assertTrue(actualDeleteRecreationByIdResult.hasBody());
        assertTrue(actualDeleteRecreationByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteRecreationByIdResult.getStatusCode());
        assertEquals("Recreation deleted.", ((ApiResponse) Objects.requireNonNull(actualDeleteRecreationByIdResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualDeleteRecreationByIdResult.getBody()).isSuccess());
        verify(recreationRepository).findById(any());
        verify(recreationRepository).save(any());
    }

    /*@Test
    void testSaveNewRecreationAddress() {
        AddressRepository addressRepository = mock(AddressRepository.class);
        when(addressRepository.save(any())).thenReturn(address);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        when(buildingRepository.save(any())).thenReturn(building);
        StreetRepository streetRepository = mock(StreetRepository.class);
        when(streetRepository.save(any())).thenReturn(street);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        when(districtRepository.save(any())).thenReturn(district);
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

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address);
        RecreationDTO recreationDTO = mock(RecreationDTO.class);
        when(recreationDTO.getAddress()).thenReturn(addressList);
        assertSame("Address(addressId=null, id=null, homeCode=null, homeNumber=null, building=null, ownerCardNumber=11111114)", recreationServiceImpl.saveNewRecreationAddress(recreationDTO));
        verify(addressRepository).save(any());
        verify(buildingRepository).save(any());
        verify(streetRepository).save(any());
        verify(districtRepository).save(any());
        verify(recreationDTO).getAddress();
    }*/
}

