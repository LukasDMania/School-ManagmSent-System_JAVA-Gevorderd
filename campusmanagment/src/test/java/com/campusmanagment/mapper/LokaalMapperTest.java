package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.LokaalCreateDTO;
import com.campusmanagment.model.Adres;
import com.campusmanagment.model.Campus;
import com.campusmanagment.model.Lokaal;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LokaalMapperTest {

    private final Logger logger = Logger.getLogger(LokaalMapperTest.class.getName());
    private final LokaalMapper lokaalMapper = Mappers.getMapper(LokaalMapper.class);

    @Test
    void lokaalToLokaalResponseDTOWithCampus() {
    }

    @Test
    void lokaalCreateDTOToLokaal() {
        LokaalCreateDTO lokaalDTO = new LokaalCreateDTO();
        lokaalDTO.setLokaalNaam("Lokaal 1");
        lokaalDTO.setCapaciteit(30);
        lokaalDTO.setLokaalType(null);
        lokaalDTO.setVerdieping(0);

        Adres adres = new Adres("Straat", "Stad", "Postcode");
        Campus campus = new Campus("Campus", adres,1);
        Lokaal lokaal1 = lokaalMapper.lokaalCreateDTOToLokaal(lokaalDTO, campus);

        assertEquals("Lokaal 1", lokaal1.getLokaalNaam());
        assertEquals(30, lokaal1.getCapaciteit());
        assertNull(lokaal1.getLokaalType());

        assertEquals(0, lokaal1.getVerdieping());
    }
}