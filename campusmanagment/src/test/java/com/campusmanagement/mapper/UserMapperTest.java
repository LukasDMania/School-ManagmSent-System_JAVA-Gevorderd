package com.campusmanagement.mapper;

import com.campusmanagement.dto.create.UserCreateDTO;
import com.campusmanagement.dto.response.ReservatieResponseDTO;
import com.campusmanagement.dto.response.UserResponseDTO;
import com.campusmanagement.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private static final Logger LOGGER = Logger.getLogger(UserMapperTest.class.getName());
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Test
    void userToUserResponseDTOWithReservaties() {
        User user = new User("Naam", "VoorNaam", LocalDate.now(), "Email");
        List<ReservatieResponseDTO> reservaties = Arrays.asList(
                new ReservatieResponseDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Commentaar 1"),
                new ReservatieResponseDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Commentaar 2")
        );

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithReservaties(user, reservaties);

        assertNotNull(userResponseDTO, "UserResponseDTO is null");
        assertEquals("Naam", userResponseDTO.getNaam(), "Naam is not equal");
        assertEquals("VoorNaam", userResponseDTO.getVoornaam(), "VoorNaam is not equal");
        assertEquals(LocalDate.now(), userResponseDTO.getGeboorteDatum(), "GeboorteDatum is not equal");
        assertEquals("Email", userResponseDTO.getEmail(), "Email is not equal");
        assertEquals(2, userResponseDTO.getReservatieResponseDTOs().size(), "Reservatie size is not equal");
        assertEquals("Commentaar 1", userResponseDTO.getReservatieResponseDTOs().getFirst().getUserCommentaar(), "Commentaar is not equal");
        assertEquals("Commentaar 2", userResponseDTO.getReservatieResponseDTOs().getLast().getUserCommentaar(), "Commentaar is not equal");
    }
    @Test
    void userToUserResponseDTOWithoutReservaties() {
        User user = new User("Naam", "VoorNaam", LocalDate.now(), "Email");
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(user);

        assertNotNull(userResponseDTO, "UserResponseDTO is null");
        assertEquals("Naam", userResponseDTO.getNaam(), "Naam is not equal");
        assertEquals("VoorNaam", userResponseDTO.getVoornaam(), "VoorNaam is not equal");
        assertEquals(LocalDate.now(), userResponseDTO.getGeboorteDatum(), "GeboorteDatum is not equal");
        assertEquals("Email", userResponseDTO.getEmail(), "Email is not equal");
        assertEquals(0, userResponseDTO.getReservatieResponseDTOs().size(), "Reservatie size is not equal");

    }


    @Test
    void userCreateDTOToUser() {
        User user = userMapper.userCreateDTOToUser(new UserCreateDTO("Naam", "VoorNaam", LocalDate.now(), "Email"));

        assertNotNull(user, "User is null");
        assertEquals("Naam", user.getNaam(), "Naam is not equal");
        assertEquals("VoorNaam", user.getVoornaam(), "VoorNaam is not equal");
        assertEquals(LocalDate.now(), user.getGeboorteDatum(), "GeboorteDatum is not equal");
        assertEquals("Email", user.getEmail(), "Email is not equal");
    }
}