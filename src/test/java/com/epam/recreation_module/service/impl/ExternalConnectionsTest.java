package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.exception.RestException;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.HTTPRequestService;
import com.epam.recreation_module.service.RecreationService;
import com.epam.recreation_module.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ExternalConnections.class, String.class})
@ExtendWith(SpringExtension.class)
class ExternalConnectionsTest {


    @MockBean
    private RecreationService recreationService;

    @MockBean
    private UserService userService;

    @Autowired
    private ExternalConnections externalConnections;

    @MockBean
    private HTTPRequestService hTTPRequestService;

    @Test
    void testCreatingNewEvent() {
        when(this.hTTPRequestService.makePOSTHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        ResponseEntity<?> actualCreatingNewEventResult = this.externalConnections.creatingNewEvent("42", "Name",
                "The characteristics of someone or something", "Start Time", "End Time");
        assertEquals("Request Accepted", actualCreatingNewEventResult.getBody());
        assertEquals("<201 CREATED Created,Request Accepted,[]>", actualCreatingNewEventResult.toString());
        assertEquals(HttpStatus.CREATED, actualCreatingNewEventResult.getStatusCode());
        assertTrue(actualCreatingNewEventResult.getHeaders().isEmpty());
        verify(this.hTTPRequestService).makePOSTHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any());
    }

    @Test
    void testCreatingNewEvent2() {
        when(this.hTTPRequestService.makePOSTHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any())).thenThrow(new RestException("An error occurred", HttpStatus.CONTINUE));
        ResponseEntity<?> actualCreatingNewEventResult = this.externalConnections.creatingNewEvent("42", "Name",
                "The characteristics of someone or something", "Start Time", "End Time");
        assertEquals("Server not available!", actualCreatingNewEventResult.getBody());
        assertEquals("<503 SERVICE_UNAVAILABLE Service Unavailable,Server not available!,[]>",
                actualCreatingNewEventResult.toString());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, actualCreatingNewEventResult.getStatusCode());
        assertTrue(actualCreatingNewEventResult.getHeaders().isEmpty());
        verify(this.hTTPRequestService).makePOSTHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any());
    }

//    @Test
//    void testMakePayment() {
//        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(HttpStatus.CONTINUE);
//        when(this.hTTPRequestService.makePOSTHTTPCallUsingHMAC((String) any(), (String) any(), (String) any(),
//                (String) any(), (String) any())).thenReturn(responseEntity);
//        assertSame(responseEntity, this.externalConnections.makePayment("Service Name",
//                "The characteristics of someone or something", 10.0, "42", "https://example.org/example"));
//        verify(this.hTTPRequestService).makePOSTHTTPCallUsingHMAC((String) any(), (String) any(), (String) any(),
//                (String) any(), (String) any());
//    }

    @Test
    void testMakePayment2() {
        when(this.hTTPRequestService.makePOSTHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any())).thenThrow(new RestException("An error occurred", HttpStatus.CONTINUE));
        ResponseEntity<?> actualMakePaymentResult = this.externalConnections.makePayment("Service Name",
                "The characteristics of someone or something", 10.0, "42", "https://example.org/example");
        assertEquals("Server not available!", actualMakePaymentResult.getBody());
        assertEquals("<503 SERVICE_UNAVAILABLE Service Unavailable,Server not available!,[]>",
                actualMakePaymentResult.toString());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, actualMakePaymentResult.getStatusCode());
        assertTrue(actualMakePaymentResult.getHeaders().isEmpty());
        verify(this.hTTPRequestService).makePOSTHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any());
    }

//    @Test
//    void testCheckUserFromCityManagement() {
//        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(HttpStatus.CONTINUE);
//        when(this.hTTPRequestService.makeGETHTTPCallUsingHMAC((String) any(), (String) any(), (String) any(),
//                (String) any())).thenReturn(responseEntity);
//        assertSame(responseEntity, this.externalConnections.checkUserFromCityManagement("42"));
//        verify(this.hTTPRequestService).makeGETHTTPCallUsingHMAC((String) any(), (String) any(), (String) any(),
//                (String) any());
//    }

    @Test
    void testCheckUserFromCityManagement2() {
        when(this.hTTPRequestService.makeGETHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any())).thenThrow(new NotFoundException("An error occurred"));
        ResponseEntity<?> actualCheckUserFromCityManagementResult = this.externalConnections
                .checkUserFromCityManagement("42");
        assertEquals("<404 NOT_FOUND Not Found,ApiResponse(message=Citizen Id not found!, success=false, roles=null,"
                + " object=null),[]>", actualCheckUserFromCityManagementResult.toString());
        assertTrue(actualCheckUserFromCityManagementResult.hasBody());
        assertTrue(actualCheckUserFromCityManagementResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualCheckUserFromCityManagementResult.getStatusCode());
        assertEquals("Citizen Id not found!",
                ((ApiResponse) actualCheckUserFromCityManagementResult.getBody()).getMessage());
        assertFalse(((ApiResponse) actualCheckUserFromCityManagementResult.getBody()).isSuccess());
        verify(this.hTTPRequestService).makeGETHTTPCallUsingHMAC((String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(), (String) ArgumentMatchers.any(),
                (String) ArgumentMatchers.any());
    }
}

