package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.CommentaryService;
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
 * Done Checked
 *
 * @author Yulchiev Abbos
 */

@ContextConfiguration(classes = {CommentaryController.class})
@ExtendWith(SpringExtension.class)
class CommentaryControllerTest {
    @Autowired
    private CommentaryController commentaryController;

    @MockBean
    private CommentaryService commentaryService;

    @Test
    void testGetCommentaries() throws Exception {
        when(this.commentaryService.getCommentaries((Long) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/commentary/all");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("recreationId", String.valueOf(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetCommentById() throws Exception {
        when(this.commentaryService.getCommentById((Long) ArgumentMatchers.any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/commentary/{commentId}", 123L);
        MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"roles\":null,\"object\":null}"));
    }

    @Test
    void testAddCommentary() throws Exception {
        when(this.commentaryService.addCommentary((CommentaryDTO) ArgumentMatchers.any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));

        CommentaryDTO commentaryDTO = new CommentaryDTO();
        commentaryDTO.setRecreationId(123L);
        commentaryDTO.setCommentText("Comment Text");
        String content = (new ObjectMapper()).writeValueAsString(commentaryDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/commentary/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"roles\":null,\"object\":null}"));
    }

    @Test
    void testEditCommentary() throws Exception {
        when(this.commentaryService.editComment((Long) ArgumentMatchers.any(), (CommentaryDTO) ArgumentMatchers.any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));

        CommentaryDTO commentaryDTO = new CommentaryDTO();
        commentaryDTO.setRecreationId(123L);
        commentaryDTO.setCommentText("Comment Text");
        String content = (new ObjectMapper()).writeValueAsString(commentaryDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/commentary/edit/{commentId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"roles\":null,\"object\":null}"));
    }

    @Test
    void testEditMyCommentary() throws Exception {
        when(this.commentaryService.editMyComment((Long) ArgumentMatchers.any(), (CommentaryDTO) ArgumentMatchers.any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));

        CommentaryDTO commentaryDTO = new CommentaryDTO();
        commentaryDTO.setRecreationId(123L);
        commentaryDTO.setCommentText("Comment Text");
        String content = (new ObjectMapper()).writeValueAsString(commentaryDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/commentary/editMy/{commentId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"roles\":null,\"object\":null}"));
    }

    @Test
    void testDeleteCommentary() throws Exception {
        when(this.commentaryService.deleteComment((Long) ArgumentMatchers.any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", false));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/commentary/delete/{commentId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"roles\":null,\"object\":null}"));
    }

    @Test
    void testDeleteMyCommentary() throws Exception {
        when(this.commentaryService.deleteMyComment((Long) ArgumentMatchers.any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", false));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/commentary/deleteMy/{commentId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"roles\":null,\"object\":null}"));
    }
}

