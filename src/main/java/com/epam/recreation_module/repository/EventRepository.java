package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select *\n" +
            "from event\n" +
            "where is_deleted IS NULL\n" +
            "  AND confirmed = true", nativeQuery = true)
    Page<Event> findAllConfirmed(Pageable pageable);

}
