package com.campusmanagement.integration;

import com.campusmanagement.dto.create.ReservatieCreateDTO;
import com.campusmanagement.dto.response.ReservatieResponseDTO;
import com.campusmanagement.model.*;
import com.campusmanagement.repository.CampusRepository;
import com.campusmanagement.repository.LokaalRepository;
import com.campusmanagement.repository.ReservatieRepository;
import com.campusmanagement.repository.UserRepository;
import com.campusmanagement.util.LokaalType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservatieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservatieRepository reservatieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LokaalRepository lokaalRepository;

    @Autowired
    private CampusRepository campusRepository;

    private Reservatie testReservatie;
    private User testUser;
    private Lokaal testLokaal;

    @BeforeEach
    public void setup() {
        reservatieRepository.deleteAll();
        userRepository.deleteAll();
        lokaalRepository.deleteAll();

        testUser = new User();
        testUser.setNaam("testNaam");
        testUser.setVoornaam("testVoornaam");
        testUser.setEmail("testuser@example.com");
        testUser = userRepository.save(testUser);

        Campus testCampus = new Campus();
        testCampus.setCampusNaam("Test Campus");
        testCampus.setAdres(new Adres("straat", "stad", "postcode"));
        testCampus = campusRepository.save(testCampus);

        testLokaal = new Lokaal();
        testLokaal.setLokaalNaam("Test Lokaal");
        testLokaal.setLokaalType(LokaalType.CAFETARIA);
        testLokaal.setCampus(testCampus);
        testLokaal = lokaalRepository.save(testLokaal);

        testReservatie = new Reservatie();
        testReservatie.setStartTijdstip(LocalDateTime.now().plusDays(1));
        testReservatie.setEindTijdstip(LocalDateTime.now().plusDays(1).plusHours(2));
        testReservatie.setUserCommentaar("Test Commentaar");
        testReservatie.setUser(testUser);
        testReservatie.setLokalen(Collections.singletonList(testLokaal));
        testReservatie = reservatieRepository.save(testReservatie);
    }

    @Test
    public void testGetAllReservaties() throws Exception {
        mockMvc.perform(get("/reservatie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userCommentaar").value("Test Commentaar"));
    }

    @Test
    public void testGetReservatieById() throws Exception {
        mockMvc.perform(get("/reservatie/" + testReservatie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userCommentaar").value("Test Commentaar"));
    }

    @Test
    public void testCreateReservatie() throws Exception {
        ReservatieCreateDTO createDTO = new ReservatieCreateDTO();
        createDTO.setStartTijdstip(LocalDateTime.now().plusDays(2));
        createDTO.setEindTijdstip(LocalDateTime.now().plusDays(2).plusHours(2));
        createDTO.setUserCommentaar("New Reservatie");
        createDTO.setUserId(testUser.getId());
        createDTO.setLokalenIds(Collections.singletonList(testLokaal.getId()));

        ResultActions result = mockMvc.perform(post("/reservatie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userCommentaar").value("New Reservatie"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        ReservatieResponseDTO reservatieResponseDTO = objectMapper.readValue(responseBody, ReservatieResponseDTO.class);



        Reservatie savedReservatie = reservatieRepository.findByUserCommentaar(reservatieResponseDTO.getUserCommentaar()).orElseThrow();
        assertEquals("New Reservatie", savedReservatie.getUserCommentaar());
    }
    @Test
    public void testCreateReservatieWithInvalidData() throws Exception {
        ReservatieCreateDTO invalidDTO = new ReservatieCreateDTO();

        mockMvc.perform(post("/reservatie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateReservatie() throws Exception {
        ReservatieCreateDTO updateDTO = new ReservatieCreateDTO();
        updateDTO.setStartTijdstip(LocalDateTime.now().plusDays(3));
        updateDTO.setEindTijdstip(LocalDateTime.now().plusDays(3).plusHours(2));
        updateDTO.setUserCommentaar("Updated Reservatie");
        updateDTO.setUserId(testUser.getId());
        updateDTO.setLokalenIds(Collections.singletonList(testLokaal.getId()));

        mockMvc.perform(put("/reservatie/" + testReservatie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userCommentaar").value("Updated Reservatie"));

        Reservatie updatedReservatie = reservatieRepository.findById(testReservatie.getId()).orElseThrow();
        assertEquals("Updated Reservatie", updatedReservatie.getUserCommentaar());
    }

    @Test
    public void testDeleteReservatie() throws Exception {
        mockMvc.perform(delete("/reservatie/" + testReservatie.getId()))
                .andExpect(status().isNoContent());

        assertFalse(reservatieRepository.existsById(testReservatie.getId()));
    }


}