package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.model.DTO.OrderRecreationDTO;
import com.epam.recreation_module.model.DTO.OrderTicketDTO;
import com.epam.recreation_module.model.DTO.PaymentConfirmationDTO;
import com.epam.recreation_module.model.DTO.PaymentDTO;
import com.epam.recreation_module.model.*;
import com.epam.recreation_module.model.enums.OrderFor;
import com.epam.recreation_module.model.payload.MadeOrdereRecreations;
import com.epam.recreation_module.model.payload.OrderedTickets;
import com.epam.recreation_module.model.payload.UserOrderedRecreation;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.*;
import com.epam.recreation_module.service.OrderService;
import lombok.SneakyThrows;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    final OrderRepository orderRepository;
    final TicketRepository ticketRepository;
    final OrderedTicketsRepository orderedTicketsRepository;
    final EventRepository eventRepository;
    final ExternalConnections externalConnections;
    final RecreationRepository recreationRepository;
    final OrderRecreationRepository orderRecreationRepository;
    final UsersOrderedRecreationsRepository usersOrderedRecreationsRepository;
    final MadeOrderRepository madeOrderRepository;
    final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, TicketRepository ticketRepository,
                            OrderedTicketsRepository orderedTicketsRepository, EventRepository eventRepository,
                            ExternalConnections externalConnections, RecreationRepository recreationRepository,
                            OrderRecreationRepository orderRecreationRepository,
                            UsersOrderedRecreationsRepository usersOrderedRecreationsRepository,
                            MadeOrderRepository madeOrderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.orderedTicketsRepository = orderedTicketsRepository;
        this.eventRepository = eventRepository;
        this.externalConnections = externalConnections;
        this.recreationRepository = recreationRepository;
        this.orderRecreationRepository = orderRecreationRepository;
        this.usersOrderedRecreationsRepository = usersOrderedRecreationsRepository;
        this.madeOrderRepository = madeOrderRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<?> getMyTickets() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<OrderedTickets> orderByUser = orderedTicketsRepository.findOrderedTicketsByUser(user.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("My Recreations", true, orderByUser));
        } catch (NotFoundException e) {
            throw new NotFoundException("Tickets not found!");
        }
    }

    @Override
    public ResponseEntity<?> getMyRecreations() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserOrderedRecreation> orderByUser = usersOrderedRecreationsRepository.userOrderedRecreations(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("My Recreations", true, orderByUser));
    }

    @Override
    public ResponseEntity<?> getOrdersByRecreationId(Long id) {
        List<MadeOrdereRecreations> orderList = madeOrderRepository.findAllByOrderByRecreationId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Orders", true, orderList));
    }

    @Override
    public ResponseEntity<?> orderTicket(OrderTicketDTO orderTicketDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        double price = 0;
        List<Ticket> ticketList = new ArrayList<>();
        for (Long ticketId : orderTicketDTO.getTicketsId()) {
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new NotFoundException("Invalid ticket with id: " + ticketId));
            Event event = eventRepository.findById(ticket.getEventId())
                    .orElseThrow(() -> new NotFoundException("Invalid Event with id: " + ticket.getEventId()));

            LocalDateTime time = LocalDateTime.now();
            boolean before = event.getEndTime().isBefore(time);
            if (before) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event Already finished");
            price += ticket.getPrice();
            ticket.setBought(true);
            ticketList.add(ticket);
        }
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(price);
        order.setCreationDate(LocalDateTime.now());
        order.setTickets(ticketList);
        order.setBookingDate(null);
        order.setOrderFor(OrderFor.TICKET);

        Order savedOrder = orderRepository.save(order);
        for (Ticket ticket : ticketList) {
            ticket.setOrder(savedOrder);
        }
        ticketRepository.saveAll(ticketList);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(savedOrder.getId());

    }

    @Override
    public ResponseEntity<?> orderRecreation(OrderRecreationDTO orderRecreationDTO) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recreation recreation = recreationRepository.findById(orderRecreationDTO.getRecreationId())
                .orElseThrow(() -> new NotFoundException("Recreation not found with id: " + orderRecreationDTO.getRecreationId()));

        List<String> visitingTimes = orderRecreationDTO.getVisitingTime();
        String time = visitingTimes.get(0);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime visitingTime = LocalDateTime.parse(time, format);
        LocalDateTime leavingTime;

        int minute = visitingTime.getMinute();
        double min = orderRecreationDTO.getVisitingDuration() / 0.5;
        if (minute >= 30 && min % 2 != 0) {
            leavingTime = visitingTime.plusHours((int) orderRecreationDTO.getVisitingDuration() + 1).minusMinutes(30);
        } else {
            leavingTime = visitingTime.plusHours((long) orderRecreationDTO.getVisitingDuration()).plusMinutes(minute);
        }
        double price = recreation.getPrice() * orderRecreationDTO.getVisitingDuration() * orderRecreationDTO.getVisitorsNumber();
        Order order = new Order();
        order.setOrderFor(OrderFor.RECREATION);
        order.setUser(user);
        order.setPaid(false);
        order.setCreationDate(LocalDateTime.now());
        order.setTotalPrice(price);
        Order savedOrder = orderRepository.save(order);

        OrderRecreation orderRecreation = new OrderRecreation();
        orderRecreation.setRecreationId(recreation.getId());
        orderRecreation.setVisitingTime(visitingTime);
        orderRecreation.setLeavingTime(leavingTime);
        orderRecreation.setVisitorsNumber(orderRecreationDTO.getVisitorsNumber());
        orderRecreation.setOrderId(savedOrder.getId());
        orderRecreation.setPrice(price);
        orderRecreationRepository.save(orderRecreation);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiResponse("Your order successfully finished!", true, savedOrder.getId()));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> payForOrder(PaymentDTO paymentDTO) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("Invalid Order Id"));
        ResponseEntity<?> responseEntity = externalConnections.makePayment(
                "RECREATION",
                "Please pay in 24 hours",
                order.getTotalPrice(),
                user.getCitizenId(),
                paymentDTO.getRedirectUrl() + "/" + paymentDTO.getOrderId());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        } else return ResponseEntity.status(HttpStatus.CONFLICT).body(responseEntity.getBody());
    }

    @Override
    public ResponseEntity<?> paymentConfirmation(PaymentConfirmationDTO paymentConfirmationDTO) {
        Order order = orderRepository.getUserLastOrder(paymentConfirmationDTO.getCitizenCardId()).orElseThrow(() -> new NotFoundException("Invalid Order Id"));
        if (order.getTotalPrice() != paymentConfirmationDTO.getAmount()) {
            throw new TypeMismatchException("Payment amount not match!");
        }
        try {
            String orderFor = order.getOrderFor().toString();
            boolean success = paymentConfirmationDTO.isSuccess();
            if (orderFor.equals(OrderFor.TICKET.toString())) {
                List<Ticket> ticketList = ticketRepository.findAllByOrder(order);
                if (success) {
                    order.setBookingDate(LocalDateTime.now());
                    order.setPaid(true);
                    for (Ticket ticket : ticketList) {
                        ticket.setBought(true);
                    }
                    ticketRepository.saveAll(ticketList);
                    orderRepository.save(order);
                    return ResponseEntity.ok().body(new ApiResponse("Payment is successfully made!", true));
                } else {
                    for (Ticket ticket : ticketList) {
                        ticket.setBought(false);
                        ticket.setOrder(new Order());
                    }
                    ticketRepository.saveAll(ticketList);
                    order.setPaid(false);
                    order.setBookingDate(LocalDateTime.now());
                    orderRepository.save(order);
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Order canceled!");
                }
            } else if (orderFor.equals(OrderFor.RECREATION.toString())) {
                if (success) {
                    order.setBookingDate(LocalDateTime.now());
                    order.setPaid(true);
                    orderRepository.save(order);
                    return ResponseEntity.ok().body(new ApiResponse("Payment is successfully made!", true));
                } else {
                    order.setPaid(false);
                    order.setBookingDate(LocalDateTime.now());
                    orderRepository.save(order);
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Order canceled!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid Payment Type!");
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("Order data not found!");
        }
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Invalid Order Id:" + orderId));
        if (order.getOrderFor().equals(OrderFor.TICKET)) {
            List<Ticket> ticketList = ticketRepository.findAllByOrder(order);
            for (Ticket ticket : ticketList) {
                ticket.setBought(false);
                ticket.setOrder(null);
            }
            ticketRepository.saveAll(ticketList);
            orderRepository.deleteById(order.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Order canceled!", true));
        } else {
            OrderRecreation orderRecreation = orderRecreationRepository.findOrderRecreationByOrderId(orderId);
            orderRecreationRepository.deleteById(orderRecreation.getId());
            orderRepository.deleteById(order.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Order canceled!", false));
        }
    }
}
