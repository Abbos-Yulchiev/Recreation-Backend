package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.AddressRepository;
import com.epam.recreation_module.repository.BuildingRepository;
import com.epam.recreation_module.repository.DistrictRepository;
import com.epam.recreation_module.repository.HMACSecretKeyRepository;
import com.epam.recreation_module.repository.RecreationRepository;
import com.epam.recreation_module.repository.RoleRepository;
import com.epam.recreation_module.repository.StreetRepository;
import com.epam.recreation_module.repository.UserRepository;
import com.epam.recreation_module.security.hmac.HMACUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    User user = new User();

    @BeforeEach
    void setUp() {
        user.setId(123L);
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setUsername("username");
        user.setPassword("123");
        user.setCitizenId("12345678");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setDeleted(false);
        user.setEnabled(true);
        user.setRoles(new HashSet<>());
    }

    @Test
    void testGetAllUsers() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualAllUsers = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).getAllUsers(1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAllUsers.toString());
        assertTrue(((PageImpl) Objects.requireNonNull(actualAllUsers.getBody())).toList().isEmpty());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(HttpStatus.OK, actualAllUsers.getStatusCode());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testGetUserById() {

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(user));
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualUserById = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).getUserById(123L);
        assertEquals("<200 OK OK,User(citizenId=12345678, username=username, firstName=Firstname, lastName=Lastname, password=123,"
                + " roles=[], deleted=false, isAccountNonExpired=true, isAccountNonLocked=true, isCredentialsNonExpired=true,"
                + " isEnabled=true),[]>", actualUserById.toString());
        assertTrue(actualUserById.hasBody());
        assertTrue(actualUserById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUserById.getStatusCode());
        verify(userRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testGetUserById2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        assertThrows(NotFoundException.class,
                () -> (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                        .getUserById(123L));
        verify(userRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testRegister() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(user));
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));
        ResponseEntity<?> actualRegisterResult = userServiceImpl
                .register(new UserDTO("12345678", "username", "123", "123", "Role"));
        assertEquals("Username already exist!", actualRegisterResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Username already exist!,[]>", actualRegisterResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualRegisterResult.getStatusCode());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        verify(userRepository).findByUsername(ArgumentMatchers.any());
    }

    /*@Test
    void testRegister2() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(  any())).thenReturn(Optional. empty());
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))));
        ResponseEntity<?> actualRegisterResult = userServiceImpl
                .register(new UserDTO("12345678", "username", "123", "123", "Role"));
        assertEquals("Error occurred!", actualRegisterResult.getBody());
        assertEquals("<406 NOT_ACCEPTABLE Not Acceptable,Error occurred!,[]>", actualRegisterResult.toString());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualRegisterResult.getStatusCode());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        verify(userRepository).findByUsername(  any());
    }*/

    @Test
    void testEditUser() {

        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsernameAndIdNot(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1));
        ResponseEntity<?> actualEditUserResult = userServiceImpl.editUser(123L,
                new UserDTO("12345678", "username", "123", "123", "Role"));
        assertEquals("Username already existed!", actualEditUserResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Username already existed!,[]>", actualEditUserResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualEditUserResult.getStatusCode());
        assertTrue(actualEditUserResult.getHeaders().isEmpty());
        verify(userRepository).existsByUsernameAndIdNot(ArgumentMatchers.any(), ArgumentMatchers.any());
        verify(userRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testDeleteUser() {

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(user));
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualDeleteUserResult = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).deleteUser(123L);
        assertEquals(
                "<200 OK OK,ApiResponse(message=User Successfully deleted!, success=true, roles=null," + " object=null),[]>",
                actualDeleteUserResult.toString());
        assertTrue(actualDeleteUserResult.hasBody());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteUserResult.getStatusCode());
        assertEquals("User Successfully deleted!", ((ApiResponse) Objects.requireNonNull(actualDeleteUserResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualDeleteUserResult.getBody()).isSuccess());
        verify(userRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testDeleteUser2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        assertThrows(NotFoundException.class,
                () -> (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                        .deleteUser(123L));
        verify(userRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testActivateUser() {

        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        ResponseEntity<?> actualActivateUserResult = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1))).activateUser(123L);
        assertEquals(
                "<202 ACCEPTED Accepted,ApiResponse(message=User activated!, success=true, roles=null," + " object=null),[]>",
                actualActivateUserResult.toString());
        assertTrue(actualActivateUserResult.hasBody());
        assertTrue(actualActivateUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.ACCEPTED, actualActivateUserResult.getStatusCode());
        assertEquals("User activated!", ((ApiResponse) Objects.requireNonNull(actualActivateUserResult.getBody())).getMessage());
        assertTrue(((ApiResponse) actualActivateUserResult.getBody()).isSuccess());
        verify(userRepository).findById(ArgumentMatchers.any());
        verify(userRepository).save(ArgumentMatchers.any());
    }

    @Test
    void testActivateUser2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        assertThrows(NotFoundException.class,
                () -> (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                        .activateUser(123L));
        verify(userRepository).findById(ArgumentMatchers.any());
    }

    @Test
    void testFindByLogin() {

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        assertSame(user,
                (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                        .findByLogin("Login"));
        verify(userRepository).findByUsername(any());
    }

    @Test
    void testFindByLogin2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(any())).thenThrow(new NotFoundException("An error occurred"));
        RoleRepository roleRepository = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        HTTPRequestServiceImpl httpRequestService = new HTTPRequestServiceImpl(
                new HMACUtil(mock(HMACSecretKeyRepository.class)));
        UserRepository userRepository1 = mock(UserRepository.class);
        RoleRepository roleRepository1 = mock(RoleRepository.class);
        Argon2PasswordEncoder passwordEncoder1 = new Argon2PasswordEncoder();
        UserServiceImpl userService = new UserServiceImpl(userRepository1, roleRepository1, passwordEncoder1,
                new ExternalConnections(null, null, null, userService1, recreationService1));

        RecreationRepository recreationRepository = mock(RecreationRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        BuildingRepository buildingRepository = mock(BuildingRepository.class);
        StreetRepository streetRepository = mock(StreetRepository.class);
        DistrictRepository districtRepository = mock(DistrictRepository.class);
        User actualFindByLoginResult = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null, userService1, recreationService1)), userService1, recreationService1)))
                .findByLogin("Login");
        assertTrue(actualFindByLoginResult.isEnabled());
        assertFalse(actualFindByLoginResult.isDeleted());
        assertTrue(actualFindByLoginResult.isCredentialsNonExpired());
        assertTrue(actualFindByLoginResult.isAccountNonLocked());
        assertTrue(actualFindByLoginResult.isAccountNonExpired());
        verify(userRepository).findByUsername(any());
    }
}