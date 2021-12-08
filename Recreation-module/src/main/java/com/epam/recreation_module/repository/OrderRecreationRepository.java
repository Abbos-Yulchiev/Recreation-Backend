package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.OrderRecreation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRecreationRepository extends JpaRepository<OrderRecreation, Long> {

    OrderRecreation findOrderRecreationByOrderId(Long orderId);
}
