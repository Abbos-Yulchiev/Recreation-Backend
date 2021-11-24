package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
