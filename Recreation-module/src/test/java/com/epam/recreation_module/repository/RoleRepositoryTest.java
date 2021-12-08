package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.enums.RoleName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;
    Role admin = new Role();
    Role user = new Role();

    @BeforeEach
    void setUp() {
        admin.setDescription("Controller person of the system");
        admin.setName(RoleName.ADMIN.toString());
        user.setDescription("User of the system");
        user.setName(RoleName.USER.toString());
        roleRepository.save(admin);
        roleRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void findByNameTrue() {
        String admin = "ADMIN";
        String user = "USER";
        assertEquals(roleRepository.findByName(admin), this.admin);
        assertEquals(roleRepository.findByName(user), this.user);
    }
}