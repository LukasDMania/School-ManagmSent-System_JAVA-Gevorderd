package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.LokaalCreateDTO;
import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import com.campusmanagment.model.Adres;
import com.campusmanagment.model.Campus;
import com.campusmanagment.model.Lokaal;
import com.campusmanagment.model.Reservatie;
import com.campusmanagment.util.LokaalType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LokaalMapperTest {

    private final Logger logger = Logger.getLogger(LokaalMapperTest.class.getName());
    private final LokaalMapper lokaalMapper = Mappers.getMapper(LokaalMapper.class);

    @Test
    void lokaalToLokaalResponseDTOWithCampusAndReservatie() {
        Campus campus = new Campus("Campus", new Adres("Straat", "Stad", "Postcode"), 1);
        CampusResponseDTO campusResponseDTO = new CampusResponseDTO(campus);
        List<ReservatieResponseDTO> reservaties = Arrays.asList(
                new ReservatieResponseDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Commentaar 1"),
                new ReservatieResponseDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Commentaar 2")
        );

        Lokaal lokaal = new Lokaal("Lokaal 1", LokaalType.CAFETARIA, 30, 0);
        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithCampusAndReservatie(lokaal, campusResponseDTO, reservaties);

        // Assertions
        assertNotNull(lokaalResponseDTO, "LokaalResponseDTO is null");
        assertEquals("Lokaal 1", lokaalResponseDTO.getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.CAFETARIA, lokaalResponseDTO.getLokaalType(), "LokaalType is not equal");
        assertEquals(30, lokaalResponseDTO.getCapaciteit(), "Capaciteit is not equal");
        assertEquals(0, lokaalResponseDTO.getVerdieping(), "Verdieping is not equal");
        assertEquals("Campus", lokaalResponseDTO.getCampusResponseDTO().getCampusNaam(), "CampusNaam is not equal");
        assertEquals("Straat", lokaalResponseDTO.getCampusResponseDTO().getAdres().getStraat(), "Straat is not equal");
        assertEquals(2, lokaalResponseDTO.getReservatieResponseDTOs().size(), "Reservatie size is not equal");
        assertEquals("Commentaar 1", lokaalResponseDTO.getReservatieResponseDTOs().getFirst().getUserCommentaar(), "Commentaar is not equal");
        assertEquals("Commentaar 2", lokaalResponseDTO.getReservatieResponseDTOs().getLast().getUserCommentaar(), "Commentaar is not equal");
    }
    @Test
    void lokaalToLokaalResponseDTOWithReservatie() {
        List<ReservatieResponseDTO> reservaties = Arrays.asList(
                new ReservatieResponseDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Commentaar 1"),
                new ReservatieResponseDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Commentaar 2")
        );

        Lokaal lokaal = new Lokaal("Lokaal 1", LokaalType.CAFETARIA, 30, 0);
        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithReservatie(lokaal, reservaties);

        // Assertions
        assertNotNull(lokaalResponseDTO, "LokaalResponseDTO is null");
        assertEquals("Lokaal 1", lokaalResponseDTO.getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.CAFETARIA, lokaalResponseDTO.getLokaalType(), "LokaalType is not equal");
        assertEquals(30, lokaalResponseDTO.getCapaciteit(), "Capaciteit is not equal");
        assertEquals(0, lokaalResponseDTO.getVerdieping(), "Verdieping is not equal");
        assertEquals(2, lokaalResponseDTO.getReservatieResponseDTOs().size(), "Reservatie size is not equal");
        assertEquals("Commentaar 1", lokaalResponseDTO.getReservatieResponseDTOs().getFirst().getUserCommentaar(), "Commentaar is not equal");
        assertEquals("Commentaar 2", lokaalResponseDTO.getReservatieResponseDTOs().getLast().getUserCommentaar(), "Commentaar is not equal");
        assertNull(lokaalResponseDTO.getCampusResponseDTO(), "CampusResponseDTO is not null");
    }
    @Test
    void lokaalToLokaalDtoWithoutCampusAndReservatie() {
        Lokaal lokaal = new Lokaal("Lokaal 1", LokaalType.CAFETARIA, 30, 0);
        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithoutCampusAndReservatie(lokaal);

        // Assertions
        assertNotNull(lokaalResponseDTO, "LokaalResponseDTO is null");
        assertEquals("Lokaal 1", lokaalResponseDTO.getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(LokaalType.CAFETARIA, lokaalResponseDTO.getLokaalType(), "LokaalType is not equal");
        assertEquals(30, lokaalResponseDTO.getCapaciteit(), "Capaciteit is not equal");
        assertEquals(0, lokaalResponseDTO.getVerdieping(), "Verdieping is not equal");
        assertNull(lokaalResponseDTO.getCampusResponseDTO(), "CampusResponseDTO is not null");
        assertEquals(0 ,lokaalResponseDTO.getReservatieResponseDTOs().size(), "ReservatieResponseDTO is not null");
    }

    @Test
    void lokaalCreateDTOToLokaal() {
        LokaalCreateDTO lokaalDTO = new LokaalCreateDTO();
        lokaalDTO.setLokaalNaam("Lokaal 1");
        lokaalDTO.setCapaciteit(30);
        lokaalDTO.setLokaalType(LokaalType.CAFETARIA);
        lokaalDTO.setVerdieping(0);

        Adres adres = new Adres("Straat", "Stad", "Postcode");
        Campus campus = new Campus("Campus", adres,1);
        Lokaal lokaal1 = lokaalMapper.lokaalCreateDTOToLokaal(lokaalDTO, campus);

        assertEquals("Lokaal 1", lokaal1.getLokaalNaam(), "LokaalNaam is not equal");
        assertEquals(30, lokaal1.getCapaciteit(), "Capaciteit is not equal");
        assertEquals(LokaalType.CAFETARIA, lokaal1.getLokaalType(), "LokaalType is not equal");
        assertEquals(0, lokaal1.getVerdieping(), "Verdieping is not equal");
        assertEquals("Campus", lokaal1.getCampus().getCampusNaam(), "CampusNaam is not equal");
        assertEquals("Straat", lokaal1.getCampus().getAdres().getStraat(), "Straat is not equal");
    }
}