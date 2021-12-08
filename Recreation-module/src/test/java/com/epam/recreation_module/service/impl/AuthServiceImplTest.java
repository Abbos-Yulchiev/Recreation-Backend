package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.model.DTO.LoginDTO;
import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.model.enums.RoleName;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.UserRepository;
import com.epam.recreation_module.security.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Done
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private AuthenticationManager authenticationManager;

    private final User user = new User();

    @BeforeEach
    void init() {
        Set<Role> role = Collections.singleton(new Role(1, RoleName.ADMIN.toString(), "Controls users"));
        user.setId(1L);
        user.setFirstName("James");
        user.setLastName("Bond");
        user.setPassword("password");
        user.setUsername("username");
        user.setCitizenId("12345678");
        user.setRoles(role);
        user.setDeleted(false);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
    }

    @Test
    void loadUserByUsername() {
        Optional<User> optionalUser = Optional.of(user);
        given(userRepository.findByUsername(any())).willReturn(optionalUser);
        UserDetails loadedUser = authService.loadUserByUsername("username");
        Assertions.assertNotNull(loadedUser);
        Assertions.assertEquals(loadedUser.getUsername(), user.getUsername());
        Assertions.assertEquals(loadedUser.getAuthorities(), user.getAuthorities());
        Assertions.assertEquals(loadedUser.getPassword(), user.getPassword());
    }

    @Test
    void login() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("username");
        loginDTO.setPassword("password");
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, "Credentials");
        given(authenticationManager.authenticate(any())).willReturn(testingAuthenticationToken);
        ResponseEntity<?> login = authService.login(loginDTO);
        assertEquals(HttpStatus.OK, login.getStatusCode());
        assertEquals("Successfully logged!", ((ApiResponse) Objects.requireNonNull(login.getBody())).getMessage());
        assertTrue(((ApiResponse) login.getBody()).isSuccess());
        verify(jwtProvider).generateToken(any());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void authToken() {
        ResponseEntity<?> login = authService.authToken(user);
        assertEquals(HttpStatus.OK, login.getStatusCode());
        assertTrue(((ApiResponse) Objects.requireNonNull(login.getBody())).isSuccess());
    }
}