package com.epam.recreation_module.component;

import com.epam.recreation_module.model.HMACSecretKey;
import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.model.enums.ComponentName;
import com.epam.recreation_module.model.enums.HMACSecretKeys;
import com.epam.recreation_module.model.enums.Permission;
import com.epam.recreation_module.model.enums.RoleName;
import com.epam.recreation_module.repository.HMACSecretKeyRepository;
import com.epam.recreation_module.repository.RoleRepository;
import com.epam.recreation_module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final HMACSecretKeyRepository hmacSecretKeyRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(ApplicationArguments args) throws RuntimeException {

        if (initialMode.equals("always")) {
            Permission[] permissions = Permission.values();

            Role admin = roleRepository.save(new Role(
                    1,
                    RoleName.ADMIN.toString(),
                    "Controller person of the system"
            ));
            Role user = roleRepository.save(new Role(
                    2,
                    RoleName.USER.toString(),
                    "User of the system"
            ));

            userRepository.save(new User(
                    "11111112",
                    "Admin",
                    "James",
                    "William",
                    passwordEncoder.encode("admin"),
                    Collections.singleton(admin)
            ));
            userRepository.save(new User(
                    "12345678",
                    "User",
                    "Nicolea",
                    "Leonard",
                    passwordEncoder.encode("user"),
                    Collections.singleton(user)
            ));


            ComponentName[] values = ComponentName.values();
            HMACSecretKeys[] keys = HMACSecretKeys.values();
            for (int i = 0; i < values.length; i++) {
                HMACSecretKey hmacSecretKey = new HMACSecretKey();
                hmacSecretKey.setComponentName(values[i].toString());
                hmacSecretKey.setSecretKey(keys[i].secretKey);
                hmacSecretKeyRepository.save(hmacSecretKey);
            }
        }
    }
}
