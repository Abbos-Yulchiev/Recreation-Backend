package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.OrderRecreationDTO;
import com.epam.recreation_module.model.DTO.OrderTicketDTO;
import com.epam.recreation_module.model.DTO.PaymentConfirmationDTO;
import com.epam.recreation_module.model.DTO.PaymentDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<?> getMyTickets();

    ResponseEntity<?> getMyRecreations();

    ResponseEntity<?> getOrdersByRecreationId(Long id);

    ResponseEntity<?> orderTicket(OrderTicketDTO orderTicketDTO);

    ResponseEntity<?> orderRecreation(OrderRecreationDTO orderRecreationDTO);

    ResponseEntity<?> payForOrder(PaymentDTO paymentDTO);

    ResponseEntity<?> paymentConfirmation(PaymentConfirmationDTO paymentConfirmationDTO);

    ResponseEntity<?> cancelOrder(Long orderId);

}
