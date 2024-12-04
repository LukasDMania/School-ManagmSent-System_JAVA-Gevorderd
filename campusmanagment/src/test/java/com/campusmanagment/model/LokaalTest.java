package com.campusmanagment.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LokaalTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testLokaalObjectCreation(){
        Lokaal lokaal = new Lokaal();
        assertNotNull(lokaal, "Lokaal object should be created");
    }

    @Test
    void testSetCampus_setsLokaalCampusCorrectly() {
        Campus campus = new Campus();
        Lokaal lokaal = new Lokaal();
        lokaal.setCampus(campus);
        assertEquals(campus, lokaal.getCampus());
    }

    // Validation tests
}