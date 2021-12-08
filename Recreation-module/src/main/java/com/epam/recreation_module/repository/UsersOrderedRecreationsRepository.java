package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Commentary;
import com.epam.recreation_module.model.Order;
import com.epam.recreation_module.model.payload.UserOrderedRecreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersOrderedRecreationsRepository extends JpaRepository<UserOrderedRecreation, Long> {

    @Query(value = "SELECT ol.id,\n" +
            "       booking_date,\n" +
            "       creation_date,\n" +
            "       paid,\n" +
            "       total_price,\n" +
            "       r.name,\n" +
            "       r.price,\n" +
            "       visiting_time,\n" +
            "       leaving_time,\n" +
            "       visitors_number\n" +
            "FROM order_list ol\n" +
            "         JOIN order_recreation ors ON ors.order_id = ol.id\n" +
            "         JOIN recreation r ON r.id = ors.recreation_id\n" +
            "WHERE order_for = 'RECREATION'\n" +
            "  AND user_id = ?1", nativeQuery = true)
    List<UserOrderedRecreation> userOrderedRecreations(Long userId);
}
