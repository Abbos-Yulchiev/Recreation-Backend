package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.payload.OrderedTickets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Done
 */
@DataJpaTest
class OrderedTicketsRepositoryTest {
    
    @Autowired
    private OrderedTicketsRepository orderedTicketsRepository;

    @Test
    void testFindOrderedTicketsByUser() {
        OrderedTickets orderedTickets = new OrderedTickets();
        orderedTickets.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        orderedTickets.setPaid(true);
        orderedTickets.setPrice(10.0);
        orderedTickets.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        orderedTickets.setName("Name");

        OrderedTickets orderedTickets1 = new OrderedTickets();
        orderedTickets1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        orderedTickets1.setPaid(true);
        orderedTickets1.setPrice(10.0);
        orderedTickets1.setBookingDate(LocalDateTime.of(1, 1, 1, 1, 1));
        orderedTickets1.setName("Name");
        this.orderedTicketsRepository.save(orderedTickets);
        this.orderedTicketsRepository.save(orderedTickets1);
        assertTrue(this.orderedTicketsRepository.findOrderedTicketsByUser(1L).isEmpty());
    }
}

