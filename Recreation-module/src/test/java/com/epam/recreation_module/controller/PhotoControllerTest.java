package com.epam.recreation_module.controller;

import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.impl.PhotoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Done Checked
 */

@ContextConfiguration(classes = {PhotoController.class})
@ExtendWith(SpringExtension.class)
class PhotoControllerTest {

    @Autowired
    private PhotoController photoController;

    @MockBean
    private PhotoServiceImpl photoServiceImpl;

    @Test
    void testGetPhotoById() throws Exception {
        doNothing().when(this.photoServiceImpl).getPhotoById(any(), any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/photo/get/{recreationId}", 123L);
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.photoController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeletePhoto() throws Exception {
        when(this.photoServiceImpl.deletePhoto(any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/photo/delete/{photoId}", 123L);
        MockMvcBuilders.standaloneSetup(this.photoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true}"));
    }
}

