package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.TicketDTO;
import com.epam.recreation_module.service.TicketService;
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

import static org.mockito.Mockito.when;

/**
 * Done checked
 */
@ContextConfiguration(classes = {TicketController.class})
@ExtendWith(SpringExtension.class)
class TicketControllerTest {

    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketService ticketService;

    @Test
    void testGetAllByEventId() throws Exception {
        when(this.ticketService.getAllByEventId( ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticket/allByEventId/{eventId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testNewTickets() throws Exception {
        when(this.ticketService.newTickets( ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        String content = (new ObjectMapper()).writeValueAsString(ticketDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ticket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditTickets() throws Exception {
        when(this.ticketService.editTickets(ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setQuantities(1);
        ticketDTO.setEventId(123L);
        ticketDTO.setPrice(10.0);
        String content = (new ObjectMapper()).writeValueAsString(ticketDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/ticket/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetUnsoldTickets() throws Exception {
        when(this.ticketService.getUnSoldTickets( ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticket/getUnsoldTickets/{eventId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

