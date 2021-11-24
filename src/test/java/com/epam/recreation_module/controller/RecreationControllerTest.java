package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.Address;
import com.epam.recreation_module.model.Building;
import com.epam.recreation_module.model.DTO.RecreationDTO;
import com.epam.recreation_module.model.District;
import com.epam.recreation_module.model.Street;
import com.epam.recreation_module.service.RecreationService;
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

@ContextConfiguration(classes = {RecreationController.class})
@ExtendWith(SpringExtension.class)
class RecreationControllerTest {
    @Autowired
    private RecreationController recreationController;

    @MockBean
    private RecreationService recreationService;

    @Test
    void testGetRecreationById() throws Exception {
        when(this.recreationService.getRecreationById((Long) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/{recreationId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetByCategory() throws Exception {
        when(this.recreationService.getByCategory((String) ArgumentMatchers.any(), (Integer) ArgumentMatchers.any(), (Integer) ArgumentMatchers.any()))
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
        when(this.recreationService.getAll((Integer) ArgumentMatchers.any(), (Integer) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/all");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAllByExist() throws Exception {
        when(this.recreationService.getAllByExist((Integer) ArgumentMatchers.any(), (Integer) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recreation/allByExist");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testCreateRecreation() throws Exception {
        when(this.recreationService.createRecreation((RecreationDTO) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        District district = new District();
        district.setId(1);
        district.setName("Name");
        district.setDistrictId(123);

        Street street = new Street();
        street.setId(1);
        street.setName("Name");
        street.setDistrict(district);
        street.setStreetId(123);

        Building building = new Building();
        building.setBuildingId(123);
        building.setStreet(street);
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");

        Address address = new Address();
        address.setBuilding(building);
        address.setHomeNumber("42");
        address.setHomeCode(1);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("42");

        RecreationDTO recreationDTO = new RecreationDTO();
        recreationDTO.setClosingTime(new String[]{"Closing Time"});
        recreationDTO.setAvailableSits(1);
        recreationDTO.setPrice(10.0);
        recreationDTO.setAddress(new Address[]{address});
        recreationDTO.setName("Name");
        recreationDTO.setOpeningTime(new String[]{"Opening Time"});
        recreationDTO.setRecreationStatus("Recreation Status");
        recreationDTO.setDescription("The characteristics of someone or something");
        recreationDTO.setRecreationCategory("Recreation Category");
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
        when(this.recreationService.editRecreation((Long) ArgumentMatchers.any(), (RecreationDTO) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        District district = new District();
        district.setId(1);
        district.setName("Name");
        district.setDistrictId(123);

        Street street = new Street();
        street.setId(1);
        street.setName("Name");
        street.setDistrict(district);
        street.setStreetId(123);

        Building building = new Building();
        building.setBuildingId(123);
        building.setStreet(street);
        building.setId(1);
        building.setBuildingNumber(10);
        building.setBuildingType("Building Type");

        Address address = new Address();
        address.setBuilding(building);
        address.setHomeNumber("42");
        address.setHomeCode(1);
        address.setId(123L);
        address.setAddressId(123L);
        address.setOwnerCardNumber("42");

        RecreationDTO recreationDTO = new RecreationDTO();
        recreationDTO.setClosingTime(new String[]{"Closing Time"});
        recreationDTO.setAvailableSits(1);
        recreationDTO.setPrice(10.0);
        recreationDTO.setAddress(new Address[]{address});
        recreationDTO.setName("Name");
        recreationDTO.setOpeningTime(new String[]{"Opening Time"});
        recreationDTO.setRecreationStatus("Recreation Status");
        recreationDTO.setDescription("The characteristics of someone or something");
        recreationDTO.setRecreationCategory("Recreation Category");
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
        when(this.recreationService.editRecreationStatus((Long) ArgumentMatchers.any(), (String) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .put("/recreation/editStatus/{recreationId}", 123L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new String()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeleteRecreation() throws Exception {
        when(this.recreationService.deleteRecreationById((Long) ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/recreation/delete/{recreationId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.recreationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

