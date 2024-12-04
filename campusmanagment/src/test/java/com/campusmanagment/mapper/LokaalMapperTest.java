package com.campusmanagment.mapper;

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
    void createLokaalDTOToLokaal() {

    }
}