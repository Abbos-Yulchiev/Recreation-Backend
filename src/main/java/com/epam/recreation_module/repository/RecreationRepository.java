package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Recreation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecreationRepository extends JpaRepository<Recreation, Long>, JpaSpecificationExecutor<Recreation> {

    Optional<Recreation> findByIdAndExistTrue(Long id);

    Page<Recreation> findAllByExistTrue(Pageable pageable);

    boolean existsByNameAndAddress_Id(String name, Long address_id);

    @Query(value = "SELECT * FROM recreation\n" +
            "WHERE recreation.exist = true\n" +
            "AND recreation_category = ?1 ", nativeQuery = true)
    Page<Recreation> findByCategory(String recreationCategory, Pageable pageable);


}
