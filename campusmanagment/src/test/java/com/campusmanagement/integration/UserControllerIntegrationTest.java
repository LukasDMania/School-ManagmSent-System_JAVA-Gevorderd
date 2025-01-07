package com.campusmanagement.integration;

import com.campusmanagement.dto.create.UserCreateDTO;
import com.campusmanagement.dto.response.UserResponseDTO;
import com.campusmanagement.model.User;
import com.campusmanagement.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();

        testUser = new User();
        testUser.setNaam("naam");
        testUser.setVoornaam("voornaam");
        testUser.setGeboorteDatum(LocalDate.now().minusYears(20));
        testUser.setEmail("testuser@example.com");
        testUser = userRepository.save(testUser);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].naam").value("naam"))
                .andExpect(jsonPath("$[0].voornaam").value("voornaam"))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/user/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.naam").value("naam"))
                .andExpect(jsonPath("$.voornaam").value("voornaam"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserCreateDTO createDTO = new UserCreateDTO();
        createDTO.setNaam("nieuweNaam");
        createDTO.setVoornaam("nieuweVoornaam");
        createDTO.setGeboorteDatum(LocalDate.now().minusYears(30));
        createDTO.setEmail("newuser@example.com");

        ResultActions result = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam").value("nieuweNaam"))
                .andExpect(jsonPath("$.voornaam").value("nieuweVoornaam"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        UserResponseDTO userResponseDTO = objectMapper.readValue(responseBody, UserResponseDTO.class);

        User savedUser = userRepository.getUserByNaamAndVoornaam(userResponseDTO.getNaam(), userResponseDTO.getVoornaam()).orElseThrow();
        assertEquals("nieuweVoornaam", savedUser.getVoornaam());
        assertEquals("newuser@example.com", savedUser.getEmail());
    }
    @Test
    public void testCreateUserWithInvalidData() throws Exception {
        UserCreateDTO invalidDTO = new UserCreateDTO();

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserCreateDTO updateDTO = new UserCreateDTO();
        updateDTO.setNaam("updatedNaam");
        updateDTO.setVoornaam("updatedVoornaam");
        updateDTO.setGeboorteDatum(LocalDate.now().minusYears(35));
        updateDTO.setEmail("updatedemail@example.com");

        mockMvc.perform(put("/user/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam").value("updatedNaam"))
                .andExpect(jsonPath("$.voornaam").value("updatedVoornaam"))
                .andExpect(jsonPath("$.email").value("updatedemail@example.com"));

        User updatedUser = userRepository.findById(testUser.getId()).orElseThrow();
        assertEquals("updatedNaam", updatedUser.getNaam());
        assertEquals("updatedemail@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/" + testUser.getId()))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(testUser.getId()));
    }


}
