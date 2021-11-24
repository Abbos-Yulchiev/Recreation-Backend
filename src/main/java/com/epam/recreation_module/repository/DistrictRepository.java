package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Integer> {
}
