package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.exception.RestException;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.HTTPRequestService;
import com.epam.recreation_module.service.RecreationService;
import com.epam.recreation_module.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Done
 */
@ContextConfiguration(classes = {ExternalConnections.class, String.class})
@ExtendWith(SpringExtension.class)
class ExternalConnectionsTest {
    @Autowired
    private ExternalConnections externalConnections;

    @MockBean
    private HTTPRequestService hTTPRequestService;

    @MockBean
    private RecreationService recreationService;

    @MockBean
    private UserService userService;

    @Test
    void testCreatingNewEvent() {
        when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        ResponseEntity<?> actualCreatingNewEventResult = externalConnections.creatingNewEvent("42", "Name",
                "The characteristics of someone or something", "Start Time", "End Time");
        assertEquals("Request Accepted", actualCreatingNewEventResult.getBody());
        assertEquals("<201 CREATED Created,Request Accepted,[]>", actualCreatingNewEventResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreatingNewEventResult.getStatusCode());
        assertTrue(actualCreatingNewEventResult.getHeaders().isEmpty());
        verify(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
    }

    @Test
    void testCreatingNewEvent2() {
        when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(), any(), any(),
                any(), any())).thenThrow(new RestException("An error occurred", HttpStatus.CONTINUE));
        ResponseEntity<?> actualCreatingNewEventResult = externalConnections.creatingNewEvent("42", "Name",
                "The characteristics of someone or something", "Start Time", "End Time");
        assertEquals("Server not available!", actualCreatingNewEventResult.getBody());
        assertEquals("<503 SERVICE_UNAVAILABLE Service Unavailable,Server not available!,[]>",
                actualCreatingNewEventResult.toString());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, actualCreatingNewEventResult.getStatusCode());
        assertTrue(actualCreatingNewEventResult.getHeaders().isEmpty());
        verify(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(),
                any(), any());
    }

    @Test
    void testCreatingNewRecreationPlace() {
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
//      when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        doReturn(responseEntity).when(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
        assertSame(responseEntity, externalConnections.creatingNewRecreationPlace("Name", 123L, "12345678"));
        verify(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
    }

    @Test
    void testCreatingNewRecreationPlace2() {
        when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any()))
                .thenThrow(new RestException("An error occurred", HttpStatus.CONTINUE));
        ResponseEntity<?> actualCreatingNewRecreationPlaceResult = externalConnections
                .creatingNewRecreationPlace("Name", 123L, "12345678");
        assertEquals("Server not available!", actualCreatingNewRecreationPlaceResult.getBody());
        assertEquals("<503 SERVICE_UNAVAILABLE Service Unavailable,Server not available!,[]>",
                actualCreatingNewRecreationPlaceResult.toString());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, actualCreatingNewRecreationPlaceResult.getStatusCode());
        assertTrue(actualCreatingNewRecreationPlaceResult.getHeaders().isEmpty());
        verify(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
    }

    @Test
    void testMakePayment() {
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
//      when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(),any(),any(),any(),any())).thenReturn(responseEntity);
        doReturn(responseEntity).when(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
        assertSame(responseEntity, externalConnections.makePayment("Service Name",
                "The characteristics of someone or something", 10.0, "42", "https://example.org/example"));
        verify(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
    }

    @Test
    void testMakePayment2() {
        when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any()))
                .thenThrow(new RestException("An error occurred", HttpStatus.CONTINUE));
        ResponseEntity<?> actualMakePaymentResult = externalConnections.makePayment("Service Name",
                "The characteristics of someone or something", 10.0, "12345678", "https://example.org/example");
        assertEquals("Server not available!", actualMakePaymentResult.getBody());
        assertEquals("<503 SERVICE_UNAVAILABLE Service Unavailable,Server not available!,[]>",
                actualMakePaymentResult.toString());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, actualMakePaymentResult.getStatusCode());
        assertTrue(actualMakePaymentResult.getHeaders().isEmpty());
        verify(hTTPRequestService).makePOSTHTTPCallUsingHMAC(any(), any(), any(), any(), any());
    }


    @Test
    void testCheckUserFromCityManagement() {
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
//      when(hTTPRequestService.makePOSTHTTPCallUsingHMAC(any(),any(),any(),any(),any())).thenReturn(responseEntity);
        doReturn(responseEntity).when(hTTPRequestService).makeGETHTTPCallUsingHMAC(any(), any(), any(), any());
        assertSame(responseEntity, this.externalConnections.checkUserFromCityManagement("42"));
        verify(hTTPRequestService).makeGETHTTPCallUsingHMAC(any(), any(), any(), any());
    }
}

