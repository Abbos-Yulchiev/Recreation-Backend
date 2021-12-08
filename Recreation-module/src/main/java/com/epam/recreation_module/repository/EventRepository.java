package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT *\n" +
            "FROM event\n" +
            "WHERE is_deleted ISNULL\n" +
            "  AND confirmed = true\n" +
            "  AND event_status = 'ACTIVE'\n" +
            "AND end_time > now()", nativeQuery = true)
    Page<Event> findAllConfirmed(Pageable pageable);
}
