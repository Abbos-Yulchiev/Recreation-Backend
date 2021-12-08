package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
