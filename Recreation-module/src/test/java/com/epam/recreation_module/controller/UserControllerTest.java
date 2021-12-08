package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.service.UserService;
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

import static org.mockito.Mockito.when;


/**
 * Done Checked
 */
@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    UserDTO userDTO = new UserDTO();

    @BeforeEach
    void init(){
        userDTO.setPassword("123");
        userDTO.setRole("USER");
        userDTO.setUsername("username");
        userDTO.setCitizenId("12345687");
        userDTO.setPrePassword("123");
    }

    @Test
    void testRegister() throws Exception {
        when(this.userService.register( ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDelete() throws Exception {
        when(this.userService.deleteUser( ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testActivateUser() throws Exception {
        when(this.userService.activateUser( ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/deactivateUser/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEdit() throws Exception {
        when(this.userService.editUser( ArgumentMatchers.any(),  ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/edit/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAll() throws Exception {
        when(this.userService.getAllUsers( ArgumentMatchers.any(),  ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/all");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetById() throws Exception {
        when(this.userService.getUserById(ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{userId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

