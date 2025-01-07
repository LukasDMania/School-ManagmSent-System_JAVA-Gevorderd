package com.campusmanagement.integration;

import com.campusmanagement.dto.create.CampusCreateDTO;
import com.campusmanagement.dto.response.CampusResponseDTO;
import com.campusmanagement.model.Adres;
import com.campusmanagement.model.Campus;
import com.campusmanagement.repository.CampusRepository;
import com.campusmanagement.service.implemented.CampusServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CampusControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private CampusServiceImpl campusService;

    private Campus testCampus;

    @BeforeEach
    public void setup() {
        campusRepository.deleteAll();
        testCampus = new Campus();
        testCampus.setCampusNaam("Test Campus");
        testCampus.setAdres(new Adres("straat", "stad", "postcode"));
        testCampus.setParkeerplaatsen(100);
        testCampus = campusRepository.save(testCampus);
    }

    @Test
    public void testGetAllCampussen() throws Exception {
        mockMvc.perform(get("/campus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].campusNaam").value("Test Campus"));
    }

    @Test
    public void testGetCampusById() throws Exception {
        mockMvc.perform(get("/campus/" + testCampus.getCampusNaam()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.campusNaam").value("Test Campus"))
                .andExpect(jsonPath("$.adres.straat").value("straat"))
                .andExpect(jsonPath("$.adres.stad").value("stad"))
                .andExpect(jsonPath("$.adres.postcode").value("postcode"));
    }

    @Test
    public void testCreateCampus() throws Exception {
        CampusCreateDTO createDTO = new CampusCreateDTO();
        createDTO.setCampusNaam("New Campus");
        createDTO.setAdres(new Adres("straat", "stad", "postcode"));

        ResultActions result = mockMvc.perform(post("/campus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.campusNaam").value("New Campus"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        CampusResponseDTO responseDTO = objectMapper.readValue(responseBody, CampusResponseDTO.class);

        Optional<Campus> savedCampus = campusRepository.findById(responseDTO.getCampusNaam());
        assertTrue(savedCampus.isPresent());
        assertEquals("New Campus", savedCampus.get().getCampusNaam());
    }
    @Test
    public void testCreateCampusWithInvalidData() throws Exception {
        CampusCreateDTO invalidDTO = new CampusCreateDTO();

        mockMvc.perform(post("/campus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCampus() throws Exception {
        CampusCreateDTO updateDTO = new CampusCreateDTO();
        updateDTO.setCampusNaam(testCampus.getCampusNaam());
        updateDTO.setParkeerplaatsen(500);
        updateDTO.setAdres(new Adres("straatUpdate", "stad", "postcode"));

        mockMvc.perform(put("/campus/" + testCampus.getCampusNaam())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parkeerplaatsen").value(500))
                .andExpect(jsonPath("$.adres.straat").value("straatUpdate"));

        Campus updatedCampus = campusRepository.findById(testCampus.getCampusNaam()).orElseThrow();
        assertEquals(500, updatedCampus.getParkeerplaatsen());
        assertEquals("straatUpdate", updatedCampus.getAdres().getStraat());
    }

    @Test
    public void testDeleteCampus() throws Exception {
        mockMvc.perform(delete("/campus/" + testCampus.getCampusNaam()))
                .andExpect(status().isNoContent());

        assertFalse(campusRepository.existsById(testCampus.getCampusNaam()));
    }
}