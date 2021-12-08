package com.epam.recreation_module.repository;

import static org.junit.jupiter.api.Assertions.assertNull;

import com.epam.recreation_module.model.OrderRecreation;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Done
 */
@ContextConfiguration(classes = {OrderRecreationRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.epam.recreation_module.model"})
@DataJpaTest
class OrderRecreationRepositoryTest {

    @Autowired
    private OrderRecreationRepository orderRecreationRepository;

    @Test
    void testFindOrderRecreationByOrderId() {

        OrderRecreation orderRecreation = new OrderRecreation();
        orderRecreation.setPrice(10.0);
        orderRecreation.setVisitingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setVisitorsNumber(10);
        orderRecreation.setRecreationId(123L);
        orderRecreation.setLeavingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation.setOrderId(123L);

        OrderRecreation orderRecreation1 = new OrderRecreation();
        orderRecreation1.setPrice(10.0);
        orderRecreation1.setVisitingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation1.setVisitorsNumber(10);
        orderRecreation1.setRecreationId(123L);
        orderRecreation1.setLeavingTime(LocalDateTime.of(1, 1, 1, 1, 1));
        orderRecreation1.setOrderId(123L);
        
        this.orderRecreationRepository.save(orderRecreation);
        this.orderRecreationRepository.save(orderRecreation1);
        assertNull(this.orderRecreationRepository.findOrderRecreationByOrderId(1L));
    }
}

