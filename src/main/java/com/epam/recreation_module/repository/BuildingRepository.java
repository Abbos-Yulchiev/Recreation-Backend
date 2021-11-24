package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
