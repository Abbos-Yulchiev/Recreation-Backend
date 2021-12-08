package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.Order;
import com.epam.recreation_module.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT count(*)\n" +
            "FROM ticket\n" +
            "where event_id = ?1", nativeQuery = true)
    Long getTicketByEventIds(Long eventId);

    @Query(value = "select is_bought from ticket\n" +
            "where id = :id", nativeQuery = true)
    boolean checkTicketIsSold(Long id);

    @Query(value = "SELECT *\n" +
            "FROM ticket\n" +
            "WHERE event_id = ?1\n" +
            "AND is_bought = false", nativeQuery = true)
    List<Ticket> unSoldTickets(Long eventId);

    @Query(value = "UPDATE ticket\n" +
            "SET price = ?1\n" +
            "WHERE event_id = ?2\n" +
            "RETURNING ticket.price", nativeQuery = true)
    List<Integer> editingTicket(double price, Long eventId);

    @Query(value = "UPDATE ticket\n" +
            "SET is_bought = true\n" +
            "WHERE event_id = ?1\n" +
            "  AND id= ?2 returning id;", nativeQuery = true)
    List<Long> updateSoldTickets(Long eventId, Long id);

    List<Ticket> findAllByOrder(Order order);
}
