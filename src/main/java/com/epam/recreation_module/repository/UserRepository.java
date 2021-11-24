package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByCitizenId(String citizenId);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
