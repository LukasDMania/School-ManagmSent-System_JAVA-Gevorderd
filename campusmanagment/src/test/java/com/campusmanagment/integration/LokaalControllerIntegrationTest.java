package com.campusmanagment.integration;

import com.campusmanagment.dto.create.LokaalCreateDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.model.Adres;
import com.campusmanagment.model.Campus;
import com.campusmanagment.model.Lokaal;
import com.campusmanagment.repository.CampusRepository;
import com.campusmanagment.repository.LokaalRepository;
import com.campusmanagment.util.LokaalType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LokaalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private LokaalRepository lokaalRepository;

    private Campus testCampus;
    private Lokaal testLokaal;

    @BeforeEach
    public void setup() {
        // Clear repositories
        lokaalRepository.deleteAll();
        campusRepository.deleteAll();

        // Create a test campus
        testCampus = new Campus();
        testCampus.setCampusNaam("Test Campus");
        testCampus.setAdres(new Adres("straat", "stad", "postcode"));
        testCampus = campusRepository.save(testCampus);

        // Create a test lokaal
        testLokaal = new Lokaal();
        testLokaal.setLokaalNaam("Test Lokaal");
        testLokaal.setLokaalType(LokaalType.CAFETARIA);
        testLokaal.setCapaciteit(30);
        testLokaal.setVerdieping(1);
        testLokaal.setCampus(testCampus);
        testLokaal = lokaalRepository.save(testLokaal);
    }

    @Test
    public void testGetAllLokalen() throws Exception {
        mockMvc.perform(get("/lokaal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lokaalNaam").value("Test Lokaal"))
                .andExpect(jsonPath("$[0].capaciteit").value(30))
                .andExpect(jsonPath("$[0].campusResponseDTO.campusNaam").value("Test Campus"));
    }

    @Test
    public void testGetLokaalById() throws Exception {
        mockMvc.perform(get("/lokaal/" + testLokaal.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lokaalNaam").value("Test Lokaal"))
                .andExpect(jsonPath("$.capaciteit").value(30))
                .andExpect(jsonPath("$.campusResponseDTO.campusNaam").value("Test Campus"));
    }

    @Test
    public void testCreateLokaal() throws Exception {
        // Prepare a new Lokaal create DTO
        LokaalCreateDTO createDTO = new LokaalCreateDTO();
        createDTO.setLokaalNaam("New Lokaal");
        createDTO.setLokaalType(LokaalType.CAFETARIA);
        createDTO.setCapaciteit(50);
        createDTO.setVerdieping(2);
        createDTO.setCampusId(testCampus.getCampusNaam());

        // Perform the create request
        ResultActions result = mockMvc.perform(post("/lokaal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lokaalNaam").value("New Lokaal"))
                .andExpect(jsonPath("$.capaciteit").value(50))
                .andExpect(jsonPath("$.verdieping").value(2));

        // Verify the lokaal was saved in the database
        String responseBody = result.andReturn().getResponse().getContentAsString();
        LokaalResponseDTO lokaalResponseDTO = objectMapper.readValue(responseBody, LokaalResponseDTO.class);

        Lokaal savedLokaal = lokaalRepository.findByLokaalNaam(lokaalResponseDTO.getLokaalNaam()).orElseThrow();
        assertEquals("New Lokaal", savedLokaal.getLokaalNaam());
        assertEquals(50, savedLokaal.getCapaciteit());
    }

    @Test
    public void testUpdateLokaal() throws Exception {
        // Prepare an update DTO
        LokaalCreateDTO updateDTO = new LokaalCreateDTO();
        updateDTO.setLokaalNaam("Updated Lokaal");
        updateDTO.setCapaciteit(40);
        updateDTO.setLokaalType(LokaalType.CAFETARIA);
        updateDTO.setVerdieping(2);
        updateDTO.setCampusId(testCampus.getCampusNaam());

        // Perform the update request
        mockMvc.perform(put("/lokaal/" + testLokaal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lokaalNaam").value("Updated Lokaal"))
                .andExpect(jsonPath("$.capaciteit").value(40))
                .andExpect(jsonPath("$.verdieping").value(2));

        // Verify the update in the database
        Lokaal updatedLokaal = lokaalRepository.findById(testLokaal.getId()).orElseThrow();
        assertEquals("Updated Lokaal", updatedLokaal.getLokaalNaam());
        assertEquals(40, updatedLokaal.getCapaciteit());
    }

    @Test
    public void testDeleteLokaal() throws Exception {
        // Perform delete request
        mockMvc.perform(delete("/lokaal/" + testLokaal.getId()))
                .andExpect(status().isNoContent());

        // Verify the lokaal was deleted from the database
        assertFalse(lokaalRepository.existsById(testLokaal.getId()));
    }

    @Test
    public void testCreateLokaalWithInvalidData() throws Exception {
        // Prepare an invalid Lokaal create DTO (missing required fields)
        LokaalCreateDTO invalidDTO = new LokaalCreateDTO();

        // Perform the create request and expect a bad request
        mockMvc.perform(post("/lokaal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
}
