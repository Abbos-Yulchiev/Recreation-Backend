package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.HMACSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HMACSecretKeyRepository extends JpaRepository<HMACSecretKey, Integer> {

    @Query(value = "select secret_key from hmacsecret_key where component_name = ?1", nativeQuery = true)
    String takeHMACSecretKeyByComponentName(String componentName);
}