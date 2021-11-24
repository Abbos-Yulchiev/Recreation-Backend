package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Integer> {
}
