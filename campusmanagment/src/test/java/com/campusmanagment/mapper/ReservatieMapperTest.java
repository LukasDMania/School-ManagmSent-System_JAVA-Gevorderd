package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.ReservatieCreateDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import com.campusmanagment.dto.response.UserResponseDTO;
import com.campusmanagment.model.Lokaal;
import com.campusmanagment.model.Reservatie;
import com.campusmanagment.model.User;
import com.campusmanagment.util.LokaalType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ReservatieMapperTest {

    private final Logger logger = Logger.getLogger(ReservatieMapperTest.class.getName());
    private final ReservatieMapper reservatieMapper = ReservatieMapper.INSTANCE;

    @Test
    void reservatieToReservatieResponseDTOWithUserAndLokalen() {
        Reservatie reservatie = new Reservatie(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Commentaar");
        UserResponseDTO userResponseDTO = new UserResponseDTO("naam", "voornaam", LocalDate.now(), "email");
        List<LokaalResponseDTO> lokalen = Arrays.asList(
                new LokaalResponseDTO("Lokaal 1", LokaalType.CAFETARIA, 30, 0),
                new LokaalResponseDTO("Lokaal 2", LokaalType.KLASLOKAAL, 20, 1)
        );

        ReservatieResponseDTO reservatieResponseDTO = reservatieMapper.reservatieToReservatieResponseDTOWithUserAndLokalen(reservatie, userResponseDTO, lokalen);

        // Assertions
        assertNotNull(reservatieResponseDTO, "ReservatieResponseDTO is null");
        assertEquals(reservatie.getStartTijdstip(), reservatieResponseDTO.getStartTijdstip(), "StartTijdstip is not equal");
        assertEquals(reservatie.getEindTijdstip(), reservatieResponseDTO.getEindTijdstip(), "EindTijdstip is not equal");
        assertEquals("Commentaar", reservatieResponseDTO.getUserCommentaar(), "UserCommentaar is not equal");
        assertEquals("naam", reservatieResponseDTO.getUserResponseDTO().getNaam(), "Naam is not equal");
        assertEquals("voornaam", reservatieResponseDTO.getUserResponseDTO().getVoornaam(), "Voornaam is not equal");
        assertEquals(userResponseDTO.getGeboorteDatum(), reservatieResponseDTO.getUserResponseDTO().getGeboorteDatum(), "Geboortedatum is not equal");
        assertEquals("email", reservatieResponseDTO.getUserResponseDTO().getEmail(), "Email is not equal");
        assertEquals(2, reservatieResponseDTO.getLokalenResponseDTOs().size(), "Lokalen size is not equal");
        assertEquals("Lokaal 1", reservatieResponseDTO.getLokalenResponseDTOs().getFirst().getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.CAFETARIA, reservatieResponseDTO.getLokalenResponseDTOs().getFirst().getLokaalType(), "LokaalType is not equal");
        assertEquals(30, reservatieResponseDTO.getLokalenResponseDTOs().getFirst().getCapaciteit(), "Capaciteit is not equal");
        assertEquals(0, reservatieResponseDTO.getLokalenResponseDTOs().getFirst().getVerdieping(), "Verdieping is not equal");
        assertEquals("Lokaal 2", reservatieResponseDTO.getLokalenResponseDTOs().getLast().getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.KLASLOKAAL, reservatieResponseDTO.getLokalenResponseDTOs().getLast().getLokaalType(), "LokaalType is not equal");
        assertEquals(20, reservatieResponseDTO.getLokalenResponseDTOs().getLast().getCapaciteit(), "Capaciteit is not equal");
        assertEquals(1, reservatieResponseDTO.getLokalenResponseDTOs().getLast().getVerdieping(), "Verdieping is not equal");


    }



    @Test
    void reservatieCreateDTOToReservatie() {
        User user = new User("naam", "voornaam", LocalDate.now(), "email");
        List<Lokaal> lokalen = Arrays.asList(
                new Lokaal("Lokaal 1", LokaalType.CAFETARIA, 30, 0),
                new Lokaal("Lokaal 2", LokaalType.KLASLOKAAL, 20, 1)
        );

        ReservatieCreateDTO reservatieCreateDTO = new ReservatieCreateDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Commentaar");
        reservatieMapper.reservatieCreateDTOToReservatie(reservatieCreateDTO, user, lokalen);

        Reservatie reservatie = reservatieMapper.reservatieCreateDTOToReservatie(reservatieCreateDTO, user, lokalen);

        // Assertions
        assertNotNull(reservatie, "Reservatie is null");
        assertEquals(reservatieCreateDTO.getStartTijdstip(), reservatie.getStartTijdstip(), "StartTijdstip is not equal");
        assertEquals(reservatieCreateDTO.getEindTijdstip(), reservatie.getEindTijdstip(), "EindTijdstip is not equal");
        assertEquals("Commentaar", reservatie.getUserCommentaar(), "UserCommentaar is not equal");
        assertEquals(user, reservatie.getUser(), "User is not equal");
        assertEquals(2, reservatie.getLokalen().size(), "Lokalen size is not equal");
        assertEquals("Lokaal 1", reservatie.getLokalen().getFirst().getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.CAFETARIA, reservatie.getLokalen().getFirst().getLokaalType(), "LokaalType is not equal");
        assertEquals(30, reservatie.getLokalen().getFirst().getCapaciteit(), "Capaciteit is not equal");
        assertEquals(0, reservatie.getLokalen().getFirst().getVerdieping(), "Verdieping is not equal");
        assertEquals("Lokaal 2", reservatie.getLokalen().getLast().getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.KLASLOKAAL, reservatie.getLokalen().getLast().getLokaalType(), "LokaalType is not equal");
        assertEquals(20, reservatie.getLokalen().getLast().getCapaciteit(), "Capaciteit is not equal");
        assertEquals(1, reservatie.getLokalen().getLast().getVerdieping(), "Verdieping is not equal");
    }


}