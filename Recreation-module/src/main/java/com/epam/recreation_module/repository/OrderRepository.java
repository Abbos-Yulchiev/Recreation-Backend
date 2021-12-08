package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT *\n" +
            "FROM order_list ol\n" +
            "        JOIN users u ON ol.user_id = u.id\n" +
            "WHERE u.citizen_id = ?1\n" +
            "ORDER BY creation_date DESC\n" +
            "LIMIT 1", nativeQuery = true)
    Optional<Order> getUserLastOrder(String citizenId);
}
