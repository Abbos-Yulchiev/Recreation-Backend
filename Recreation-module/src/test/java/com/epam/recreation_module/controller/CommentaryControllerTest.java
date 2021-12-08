package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.CommentaryDTO;
import com.epam.recreation_module.service.CommentaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

/**
 * Done Checked
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

        doReturn(new ResponseEntity<>(HttpStatus.CONTINUE)).when(commentaryService).getCommentaries(any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/commentary/all");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("recreationId", String.valueOf(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetCommentById() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.CONTINUE)).when(commentaryService).getCommentById(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/commentary/{commentId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testAddCommentary() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.CONTINUE)).when(commentaryService).addCommentary(any());
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
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeleteCommentary() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.CONTINUE)).when(commentaryService).deleteComment(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/commentary/delete/{commentId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeleteMyCommentary() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.CONTINUE)).when(commentaryService).deleteMyComment(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/commentary/deleteMy/{commentId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentaryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditCommentary() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.CONTINUE)).when(commentaryService).editComment(any(), any());
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
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

