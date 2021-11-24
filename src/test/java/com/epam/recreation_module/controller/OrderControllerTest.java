package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.OrderRecreationDTO;
import com.epam.recreation_module.model.DTO.OrderTicketDTO;
import com.epam.recreation_module.model.DTO.PaymentConfirmationDTO;
import com.epam.recreation_module.model.DTO.PaymentDTO;
import com.epam.recreation_module.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

/**
 * Done Checked
 *
 * @author Yulchiev Abbos
 */

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @Test
    void testGetMyTickets() throws Exception {
        when(this.orderService.getMyTickets()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/tickets");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetMyRecreations() throws Exception {
        when(this.orderService.getMyRecreations()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/recreation");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetOrdersByRecreationId() throws Exception {
        when(this.orderService.getOrdersByRecreationId((Long) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/ordersByRecreationId/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testOrderTicket() throws Exception {
        when(this.orderService.orderTicket((OrderTicketDTO) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        OrderTicketDTO orderTicketDTO = new OrderTicketDTO();
        orderTicketDTO.setTicketsId(new ArrayList<Long>());
        String content = (new ObjectMapper()).writeValueAsString(orderTicketDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/orderTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testOrderRecreation() throws Exception {
        when(this.orderService.orderRecreation((OrderRecreationDTO) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        OrderRecreationDTO orderRecreationDTO = new OrderRecreationDTO();
        orderRecreationDTO.setVisitingTime(new String[]{"Visiting Time"});
        orderRecreationDTO.setVisitingDuration(10.0);
        orderRecreationDTO.setVisitorsNumber(10);
        orderRecreationDTO.setRecreationId(123L);
        String content = (new ObjectMapper()).writeValueAsString(orderRecreationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/orderRecreation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testCancelOrder() throws Exception {
        when(this.orderService.cancelOrder((Long) ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/order/cancel/{orderId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testPayForOrder() throws Exception {
        when(this.orderService.payForOrder((PaymentDTO) ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setRedirectUrl("https://example.org/example");
        paymentDTO.setOrderId(123L);
        String content = (new ObjectMapper()).writeValueAsString(paymentDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testPaymentConfirmation() throws Exception {
        when(this.orderService.paymentConfirmation((PaymentConfirmationDTO) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        PaymentConfirmationDTO paymentConfirmationDTO = new PaymentConfirmationDTO();
        paymentConfirmationDTO.setMessage("Not all who wander are lost");
        paymentConfirmationDTO.setCitizenCardId("42");
        paymentConfirmationDTO.setAmount(10.0);
        paymentConfirmationDTO.setTransactionId("42");
        paymentConfirmationDTO.setSuccess(true);
        String content = (new ObjectMapper()).writeValueAsString(paymentConfirmationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/paymentConfirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

