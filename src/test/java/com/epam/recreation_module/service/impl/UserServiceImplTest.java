package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {


    @Test
    void testGetAllUsers() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any()))
                .thenReturn(new PageImpl<User>(new ArrayList<User>()));
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
        ResponseEntity<?> actualAllUsers = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).getAllUsers(1, 3);
        assertEquals("<200 OK OK,Page 1 of 1 containing UNKNOWN instances,[]>", actualAllUsers.toString());
        assertTrue(((PageImpl) actualAllUsers.getBody()).toList().isEmpty());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(HttpStatus.OK, actualAllUsers.getStatusCode());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.any());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("12345678");
        user.setId(123L);
        user.setDeleted(false);
        user.setEnabled(true);
        user.setFirstName("Firstname");
        user.setRoles(new HashSet<Role>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<User>of(user));
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
        ResponseEntity<?> actualUserById = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).getUserById(123L);
        assertEquals("<200 OK OK,User(citizenId=12345678, username=username, firstName=Firstname, lastName=Lastname, password=123,"
                + " roles=[], deleted=false, isAccountNonExpired=true, isAccountNonLocked=true, isCredentialsNonExpired=true,"
                + " isEnabled=true),[]>", actualUserById.toString());
        assertTrue(actualUserById.hasBody());
        assertTrue(actualUserById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUserById.getStatusCode());
        verify(userRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testGetUserById2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<User>empty());
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
        assertThrows(NotFoundException.class,
                () -> (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null)))))
                        .getUserById(123L));
        verify(userRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("123");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("username");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("1234678");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Firstname");
        user.setRoles(new HashSet<Role>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername((String) ArgumentMatchers.any())).thenReturn(Optional.<User>of(user));
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
        assertEquals("Username already exist!", actualRegisterResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Username already exist!,[]>", actualRegisterResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualRegisterResult.getStatusCode());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        verify(userRepository).findByUsername((String) ArgumentMatchers.any());
    }

    @Test
    void testEditUser() {
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
        Optional<User> ofResult = Optional.<User>of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsernameAndIdNot((String) ArgumentMatchers.any(), (Long) ArgumentMatchers.any())).thenReturn(true);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
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
        ResponseEntity<?> actualEditUserResult = userServiceImpl.editUser(123L,
                new UserDTO("12345678", "username", "123", "123", "Role"));
        assertEquals("Username already existed!", actualEditUserResult.getBody());
        assertEquals("<409 CONFLICT Conflict,Username already existed!,[]>", actualEditUserResult.toString());
        assertEquals(HttpStatus.CONFLICT, actualEditUserResult.getStatusCode());
        assertTrue(actualEditUserResult.getHeaders().isEmpty());
        verify(userRepository).existsByUsernameAndIdNot((String) ArgumentMatchers.any(), (Long) ArgumentMatchers.any());
        verify(userRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setLastName("Lastname");
        user.setPassword("1234");
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<User>of(user));
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
        ResponseEntity<?> actualDeleteUserResult = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).deleteUser(123L);
        assertEquals(
                "<200 OK OK,ApiResponse(message=User Successfully deleted!, success=true, roles=null," + " object=null),[]>",
                actualDeleteUserResult.toString());
        assertTrue(actualDeleteUserResult.hasBody());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteUserResult.getStatusCode());
        assertEquals("User Successfully deleted!", ((ApiResponse) actualDeleteUserResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualDeleteUserResult.getBody()).isSuccess());
        verify(userRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testDeleteUser2() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<User>empty());
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
        assertThrows(NotFoundException.class,
                () -> (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null)))))
                        .deleteUser(123L));
        verify(userRepository).findById((Long) ArgumentMatchers.any());
    }

    @Test
    void testActivateUser() {
        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("42");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setRoles(new HashSet<Role>());
        Optional<User> ofResult = Optional.<User>of(user);

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) ArgumentMatchers.any())).thenReturn(user);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(ofResult);
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
        ResponseEntity<?> actualActivateUserResult = (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                new ExternalConnections(httpRequestService, userService,
                        new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                streetRepository, districtRepository, new ExternalConnections(null, null, null))))).activateUser(123L);
        assertEquals(
                "<202 ACCEPTED Accepted,ApiResponse(message=User deactivated!, success=true, roles=null," + " object=null),[]>",
                actualActivateUserResult.toString());
        assertTrue(actualActivateUserResult.hasBody());
        assertTrue(actualActivateUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.ACCEPTED, actualActivateUserResult.getStatusCode());
        assertEquals("User deactivated!", ((ApiResponse) actualActivateUserResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualActivateUserResult.getBody()).isSuccess());
        verify(userRepository).findById((Long) ArgumentMatchers.any());
        verify(userRepository).save((User) ArgumentMatchers.any());
    }

    @Test
    void testActivateUser2() {
        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCredentialsNonExpired(true);
        user.setCitizenId("42");
        user.setId(123L);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setFirstName("Jane");
        user.setRoles(new HashSet<Role>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) ArgumentMatchers.any())).thenReturn(user);
        when(userRepository.findById((Long) ArgumentMatchers.any())).thenReturn(Optional.<User>empty());
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
        assertThrows(NotFoundException.class,
                () -> (new UserServiceImpl(userRepository, roleRepository, passwordEncoder,
                        new ExternalConnections(httpRequestService, userService,
                                new RecreationServiceImpl(recreationRepository, restTemplate, addressRepository, buildingRepository,
                                        streetRepository, districtRepository, new ExternalConnections(null, null, null)))))
                        .activateUser(123L));
        verify(userRepository).findById((Long) ArgumentMatchers.any());
    }
}
//TODO: editUser();
