package com.campusmanagement.mapper;

import com.campusmanagement.dto.create.CampusCreateDTO;
import com.campusmanagement.dto.response.CampusResponseDTO;
import com.campusmanagement.dto.response.LokaalResponseDTO;
import com.campusmanagement.model.Adres;
import com.campusmanagement.model.Campus;
import com.campusmanagement.model.Lokaal;
import com.campusmanagement.util.LokaalType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


class CampusMapperTest {

    private static final Logger LOGGER = Logger.getLogger(CampusMapperTest.class.getName());
    private final CampusMapper campusMapper = Mappers.getMapper(CampusMapper.class);

    @Test
    void campusToCampusResponseDTOWithLokalen() {
        Adres adres = new Adres("straat", "nummer", "postcode");
        Campus campus = new Campus("campus", adres, 5, true);

        List<LokaalResponseDTO> lokalen = Arrays.asList(
                new LokaalResponseDTO("lokaal1", LokaalType.KLASLOKAAL, 10, 1),
                new LokaalResponseDTO("lokaal2", LokaalType.KLASLOKAAL, 20, 2)
        );

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithLokalen(campus, lokalen);

        assertNotNull(campusResponseDTO, "CampusResponseDTO is null");
        assertEquals(campus.getCampusNaam(), campusResponseDTO.getCampusNaam(), "CampusNaam is not equal");
        assertEquals(campus.getAdres(), campusResponseDTO.getAdres(), "Adres is not equal");
        assertEquals(campus.getParkeerplaatsen(), campusResponseDTO.getParkeerplaatsen(), "Parkeerplaatsen is not equal");

        assertEquals(2, campusResponseDTO.getLokalenResponseDTOs().size(), "Lokalen size is not equal");

        LokaalResponseDTO first = campusResponseDTO.getLokalenResponseDTOs().getFirst();
        assertEquals("lokaal1", first.getLokaalNaam(), "Nested LokaalDTO's LokaalNaam is not equal");
        assertEquals(LokaalType.KLASLOKAAL, first.getLokaalType(), "Nested LokaalDTO's LokaalType is not equal");
        assertEquals(10, first.getCapaciteit(), "Nested LokaalDTO's Capaciteit is not equal");
        assertEquals(1, first.getVerdieping(), "Nested LokaalDTO's Verdieping is not equal");
    }

    @Test
    void campusCreateDTOToCampus() {
        Adres adres = new Adres("straat", "nummer", "postcode");
        Campus campus = new Campus("campus", adres, 5, true);

        CampusCreateDTO campusCreateDTO = new CampusCreateDTO("campus", adres, 5);

        Campus campusFromDTO = campusMapper.campusCreateDTOToCampus(campusCreateDTO);

        assertNotNull(campusFromDTO, "Campus is null");
        assertEquals(campus.getCampusNaam(), campusFromDTO.getCampusNaam(), "CampusNaam is not equal");
        assertEquals(campus.getAdres(), campusFromDTO.getAdres(), "Adres is not equal");
        assertEquals(campus.getParkeerplaatsen(), campusFromDTO.getParkeerplaatsen(), "Parkeerplaatsen is not equal");
    }
}