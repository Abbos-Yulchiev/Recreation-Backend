package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT *\n" +
            "FROM order_list\n" +
            "JOIN users u ON order_list.user_id = u.id\n" +
            "WHERE u.citizen_id = ?1\n" +
            "ORDER BY creation_date DESC\n" +
            "LIMIT 1;", nativeQuery = true)
    Optional<Order> getUserLastOrder(String citizenId);

    @Query(value = "SELECT o.id, booking_date, creation_date, paid, e.name, t.price\n" +
            "FROM order_list o\n" +
            "         JOIN ticket t ON o.id = t.order_id\n" +
            "         JOIN event e ON e.id = t.event_id\n" +
            "WHERE order_for = 'TICKET'" +
            "  AND user_id = ?1", nativeQuery = true)
    List<Object> findOrderedTicketsByUser(Long userId);

    @Query(value = "SELECT ol.id, booking_date, creation_date, paid, total_price, " +
            "r.name, r.price, visiting_time, leaving_time, visitors_number\n" +
            "FROM order_list ol\n" +
            "JOIN order_recreation ors ON ors.order_id = ol.id\n" +
            "JOIN recreation r ON r.id = ors.recreation_id\n" +
            "WHERE order_for = 'RECREATION'\n" +
            "  AND user_id = ?1", nativeQuery = true)
    List<Object> findOrderedRecreationsByUser(Long userId);

    @Query(value = "SELECT ol.id as orderId, ol.paid, ol.total_price orderTotalPrice, ol.creation_date, ol.booking_date,\n" +
            "       ors.visitors_number, ors.visiting_time, ors.leaving_time, r.price as recreationPrice,\n" +
            "r.name as recreationName, u.first_name, u.last_name\n" +
            "FROM order_recreation ors\n" +
            "         FULL JOIN order_list ol ON ors.order_id = ol.id\n" +
            "         FULL JOIN recreation r on r.id = ors.recreation_id\n" +
            "         FULL JOIN users u on ol.user_id = u.id\n" +
            "where recreation_id = ?1", nativeQuery = true)
    List<Object> findAllByOrderByRecreationId(Long recreationId);
}
