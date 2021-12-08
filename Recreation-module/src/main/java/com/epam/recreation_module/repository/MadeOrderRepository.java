package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.payload.MadeOrdereRecreations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MadeOrderRepository extends JpaRepository<MadeOrdereRecreations, Long> {

    @Query(value = "SELECT ol.id, ol.booking_date, ol.creation_date,  ol.paid, ol.total_price,\n" +
            "      r.name,  ors.visiting_time, ors.leaving_time, r.price,ors.visitors_number,\n" +
            " u.first_name, u.last_name\n" +
            "FROM order_recreation ors\n" +
            "         FULL JOIN order_list ol ON ors.order_id = ol.id\n" +
            "         FULL JOIN recreation r on r.id = ors.recreation_id\n" +
            "         FULL JOIN users u on ol.user_id = u.id\n" +
            "where recreation_id = ?1", nativeQuery = true)
    List<MadeOrdereRecreations> findAllByOrderByRecreationId(Long recreationId);
}
