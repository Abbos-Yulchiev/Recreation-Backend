package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.ConfirmEventDTO;
import com.epam.recreation_module.model.DTO.EventDTO;
import com.epam.recreation_module.model.enums.EventStatus;
import com.epam.recreation_module.model.enums.EventType;
import com.epam.recreation_module.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Done
 */
@ContextConfiguration(classes = {EventController.class})
@ExtendWith(SpringExtension.class)
class EventControllerTest {

    @Autowired
    private EventController eventController;

    @MockBean
    private EventService eventService;
    EventDTO eventDTO = new EventDTO();

    @BeforeEach
    void setUp() {
        eventDTO.setEventType(EventType.PARTY.toString());
        eventDTO.setAvailableSits(1);
        eventDTO.setStartTime(new ArrayList<>());
        eventDTO.setName("Party");
        eventDTO.setEventStatus(EventStatus.ACTIVE.toString());
        eventDTO.setEndTime(new ArrayList<>());
        eventDTO.setDescription("Description of the event");
        eventDTO.setRecreationId(new ArrayList<>());
    }

    @Test
    void testGetEventById() throws Exception {
        when(this.eventService.getEventById(any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/get/{eventId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAllEvent() throws Exception {
        when(this.eventService.getAllEvent(any(), any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/all");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetEventNotDelete() throws Exception {
        when(this.eventService.getEventNotDelete(any(), any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/event/allNotDelete");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testAddAnEvent() throws Exception {

        when(this.eventService.addAnEvent(any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        String content = (new ObjectMapper()).writeValueAsString(eventDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditEvent() throws Exception {
        when(this.eventService.editEvent(any(), any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        String content = (new ObjectMapper()).writeValueAsString(eventDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/event/edit/{eventId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeleteEvent() throws Exception {
        when(this.eventService.deleteEvent(any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/event/delete/{eventId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testConfirmEvent() throws Exception {
        when(this.eventService.confirmEvent(any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        ConfirmEventDTO confirmEventDTO = new ConfirmEventDTO();
        confirmEventDTO.setEventId(123L);
        confirmEventDTO.setConfirmed(true);
        String content = (new ObjectMapper()).writeValueAsString(confirmEventDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/event/confirmEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.eventController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

