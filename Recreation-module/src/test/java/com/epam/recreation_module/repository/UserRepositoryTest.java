package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.model.enums.RoleName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Done
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final String firstname = "Abbos";
    private static final String lastname = "Yulchiev";
    private static final String citizenId = "11111112";
    private static final String password = "1234";
    private static final String username = "username";

    User user = new User();

    @BeforeEach
    public void setUp() {

        Role role = new Role(1, RoleName.ADMIN.toString(),
                "Controller person of the system");
        user.setLastName(lastname);
        user.setPassword(password);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setUsername(username);
        user.setCredentialsNonExpired(true);
        user.setCitizenId(citizenId);
        user.setDeleted(false);
        user.setEnabled(true);
        user.setFirstName(firstname);
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(user);
    }

    @Test
    void testFindAll() {
        assertEquals(1, userRepository.findAll(Pageable.unpaged()).toList().size());
    }

    @Test
    void testFindByUsername() {
        assertTrue(userRepository.findByUsername(username).isPresent());
        assertFalse(userRepository.findByUsername("other-username").isPresent());
    }

    @Test
    void testExistsByCitizenId() {
        assertTrue(userRepository.existsByCitizenId(citizenId));
        assertFalse(userRepository.existsByCitizenId("other-citizenId"));
    }

    @Test
    void testExistsByUsernameAndIdNot() {
        assertFalse(this.userRepository.existsByUsernameAndIdNot("other-username", 1L));
    }

    @Test
    void getUserById() {
        Long id = user.getId();
        assertEquals(userRepository.findById(id), Optional.of(user));
    }

    @Test
    void findByLogin() {
        assertEquals(username, user.getUsername());
        assertEquals(lastname, user.getLastName());
        assertEquals(citizenId, user.getCitizenId());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isEnabled());
        assertTrue(user.isCredentialsNonExpired());
        assertFalse(user.isDeleted());
    }
}

