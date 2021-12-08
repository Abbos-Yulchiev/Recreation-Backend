package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.service.RecreationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
 * Done
 */
@ContextConfiguration(classes = {RecreationController.class})
@ExtendWith(SpringExtension.class)
class RecreationControllerTest {
    
    @Autowired
    private RecreationController recreationController;

    @MockBean
    private RecreationService recreationService;
    RecreationDTO recreationDTO = new RecreationDTO();

    @BeforeEach
    void init() {
        recreationDTO.setAvailableSits(1);
        recreationDTO.setPrice(10.0);
        recreationDTO.setName("Name");
        recreationDTO.setRecreationStatus("Recreation Status");
        recreationDTO.setDescription("The characteristics of someone or something");
        recreationDTO.setOpeningTime(new ArrayList<>());
        recreationDTO.setRecreationCategory("Recreation Category");
        recreationDTO.setClosingTime(new ArrayList<>());
        recreationDTO.setAddress(new ArrayList<>());
    }

    @Test
    void testGetRecreationById() throws Exception {
        when(this.recreationService.getRecreationById(ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/{recreationId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetByCategory() throws Exception {
        when(this.recreationService.getByCategory(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/byCategory")
                .param("category", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAll() throws Exception {
        when(this.recreationService.getAll(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/all");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAllByExist() throws Exception {
        when(this.recreationService.getAllByExist(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/allByExist");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testCreateRecreation() throws Exception {
        when(this.recreationService.createRecreation(ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));


        String content = (new ObjectMapper()).writeValueAsString(recreationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recreation/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditRecreation() throws Exception {
        when(this.recreationService.editRecreation(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        String content = (new ObjectMapper()).writeValueAsString(recreationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/recreation/edit/{recreationId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditRecreationStatus() throws Exception {
        when(this.recreationService.editRecreationStatus(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .put("/recreation/editStatus/{recreationId}", 123L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(""));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeleteRecreation() throws Exception {
        when(this.recreationService.deleteRecreationById(ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/recreation/delete/{recreationId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

