package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.OrderRecreationDTO;
import com.epam.recreation_module.model.DTO.OrderTicketDTO;
import com.epam.recreation_module.model.DTO.PaymentConfirmationDTO;
import com.epam.recreation_module.model.DTO.PaymentDTO;
import com.epam.recreation_module.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Class {@code CommentaryController} is an endpoint of the API which used for Orders
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/order"
 * Annotated by {@link CrossOrigin} with no parameters to <em>give permission to connection</em>
 * So that {@code CommentaryController} is accessed by sending requests to /order
 *
 * @author Yulchiev Abbos
 * @since 1.0
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Getting a user's all ordered tickets method
     * Annotated by {@link ApiOperation} with parameters: value = "Get a user's  ordered ticket""
     * Annotated by {@link GetMapping} with parameter value = /tickets
     *
     * @return <em>HTTP response</em> with Ticket list
     */
    @ApiOperation(value = "Get a user's  ordered ticket")
    @GetMapping("/tickets")
    public ResponseEntity<?> getMyTickets() {
        return orderService.getMyTickets();
    }

    /**
     * Getting a user's all ordered recreations sits method
     * Annotated by {@link ApiOperation} with parameters: value = "Get a user's  ordered ticket"
     * Annotated by {@link GetMapping} with parameter value = /recreation
     *
     * @return <em>HTTP response</em> with Recreation list
     */
    @ApiOperation(value = "Get a user's  ordered recreations sits")
    @GetMapping("/recreation")
    public ResponseEntity<?> getMyRecreations() {
        return orderService.getMyRecreations();
    }


    /**
     * Getting a user's all ordered recreations sits method
     * Annotated by {@link ApiOperation} with parameters: value = "Get orders by recreation's ID"
     * Annotated by {@link GetMapping} with parameter value = /ordersByRecreationId/{id}
     *
     * @param id - Recreation ID for get orders
     * @return - <em>HTTP</em> response with ordered recreations
     */
    @ApiOperation(value = "Get orders by recreation's ID")
    @GetMapping("/ordersByRecreationId/{id}")
    public ResponseEntity<?> getOrdersByRecreationId(@ApiParam("Recreation's ID") @PathVariable Long id) {
        return orderService.getOrdersByRecreationId(id);
    }

    /**
     * Order Tickets method
     * Annotated by {@link ApiOperation} with parameters: value = "Get orders by recreation's ID"
     * Annotated by {@link PostMapping} with parameter value = /orderTicket
     *
     * @param orderTicketDTO - object for order Tickets
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Get orders by recreation's ID")
    @PostMapping("/orderTicket")
    public ResponseEntity<?> orderTicket(@ApiParam("CommentaryDTO object")
                                         @RequestBody OrderTicketDTO orderTicketDTO) {
        return orderService.orderTicket(orderTicketDTO);
    }

    /**
     * Ordering Recreation method
     * Annotated by {@link ApiOperation} with parameters: value = "Create new order for recreation"
     * Annotated by {@link PostMapping} with parameter value = /orderRecreation
     *
     * @param orderRecreationDTO - object for order Recreation
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Create new order for recreation")
    @PostMapping("/orderRecreation")
    public ResponseEntity<?> orderRecreation(@ApiParam("OrderRecreationDTO object")
                                             @RequestBody OrderRecreationDTO orderRecreationDTO) {
        return orderService.orderRecreation(orderRecreationDTO);
    }

    /**
     * Delete(Cancel) Order method
     * Annotated by {@link ApiOperation} with parameters: value = "Delete(Cancel) order by order's ID"
     * Annotated by {@link DeleteMapping} with parameter value = /cancel/{orderId}
     *
     * @param orderId - Order ID for delete Order
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Delete(Cancel) order by order's ID")
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@ApiParam("Order id") @PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    /**
     * Create new Order Payment method
     * Annotated by {@link ApiOperation} with parameters: value = "Make pay for order"
     * Annotated by {@link PostMapping} with parameter value = /pay
     *
     * @param paymentDTO - object creat new payment
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Make pay for order")
    @PostMapping("/pay")
    public ResponseEntity<?> payForOrder(@ApiParam("PaymentDTO object")
                                         @RequestBody PaymentDTO paymentDTO) {
        return orderService.payForOrder(paymentDTO);
    }

    /**
     * Payment confirmation from Account Module method
     * Annotated by {@link ApiOperation} with parameters: value = "Payment confirmation from Account module"
     * Annotated by {@link PostMapping} with parameter value = /paymentConfirmation
     *
     * @param paymentConfirmationDTO - object for payment confirmation
     * @return - <em>HTTP</em> response with message
     */
    @ApiOperation(value = "Payment confirmation from Account module")
    @PostMapping("/paymentConfirmation")
    public ResponseEntity<?> paymentConfirmation(@ApiParam("PaymentConfirmationDTO object")
                                                 @RequestBody PaymentConfirmationDTO paymentConfirmationDTO) {
        return orderService.paymentConfirmation(paymentConfirmationDTO);
    }
}
