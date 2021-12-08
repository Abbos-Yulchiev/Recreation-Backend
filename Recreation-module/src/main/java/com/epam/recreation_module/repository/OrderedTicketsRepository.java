package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.payload.OrderedTickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedTicketsRepository extends JpaRepository<OrderedTickets, Long> {

    @Query(value = "SELECT o.id, booking_date, creation_date, paid, e.name, t.price\n" +
            "FROM order_list o\n" +
            "         JOIN ticket t ON o.id = t.order_id\n" +
            "         JOIN event e ON e.id = t.event_id\n" +
            "WHERE order_for = 'TICKET'" +
            "  AND user_id = ?1", nativeQuery = true)
    List<OrderedTickets> findOrderedTicketsByUser(Long userId);
}
