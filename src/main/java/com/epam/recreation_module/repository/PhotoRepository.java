package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query(value = "SELECT id FROM photo\n" +
            "WHERE recreation_id = ?1", nativeQuery = true)
    Long findPhotosByRecreationId(Long recreationId);
}
