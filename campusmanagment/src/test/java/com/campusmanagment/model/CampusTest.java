package com.campusmanagment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampusTest {
    private Campus campus;

    @BeforeEach
    void setUp() {
        campus = new Campus(true);
    }

    @Test
    void testCampusObjectCreation(){
        assertNotNull(campus, "Campus object should be created");
    }

    @Test
    void testSetName_throwsIllegalArgumentException_whenNameIsEmptyOrNull(){
        // Assert that an IllegalArgumentException is thrown when name is empty
        assertThrows(IllegalArgumentException.class, () -> campus.setCampusNaam(""));
        // Assert that an IllegalArgumentException is thrown when name is null
        assertThrows(IllegalArgumentException.class, () -> campus.setCampusNaam(null));
    }

    @Test
    void testGetAantalLokalen_returnsZero_whenLokalenListIsNull(){
        assertEquals(campus.getAantalLokalen(), 0);
    }
    @Test
    void testGetAantalLokalen_returnsCorrectCount_whenLokalenCountIsTwo(){
        Lokaal lokaal1 = new Lokaal();
        Lokaal lokaal2 = new Lokaal();
        campus.addLokaal(lokaal1);
        campus.addLokaal(lokaal2);

        assertEquals(campus.getAantalLokalen(), 2);
    }
    @Test
    void testAddLokaal_doesNotAddLokaal_whenLokaalIsNull(){
        campus.addLokaal(null);
        assertEquals(campus.getAantalLokalen(), 0);
    }

    @Test
    void testAddLokaal_addsLokaalToList_whenAdded(){
        Lokaal lokaal = new Lokaal();
        campus.addLokaal(lokaal);
        assertTrue(campus.getLokalen().contains(lokaal));
    }
    @Test
    void testAddLokaal_doesNotAddLokaalToList_whenLokaalIsAlreadyInList(){
        Lokaal lokaal = new Lokaal();
        campus.addLokaal(lokaal);
        campus.addLokaal(lokaal);
        assertEquals(campus.getAantalLokalen(), 1);
    }
    @Test
    void testAddLokaal_addsCampusToLokaal_whenAdded(){
        Lokaal lokaal = new Lokaal();
        campus.addLokaal(lokaal);
        assertEquals(lokaal.getCampus(), campus);
    }

    @Test
    void testRemoveLokaal_removesLokaalFromList_whenLokaalIsInList(){
        Lokaal lokaal = new Lokaal();
        campus.addLokaal(lokaal);
        campus.removeLokaal(lokaal);
        assertFalse(campus.getLokalen().contains(lokaal));
    }
    @Test
    void testRemoveLokaal_doesNotRemoveLokaalFromList_whenLokaalIsNotInList(){
        Lokaal lokaal = new Lokaal();
        Lokaal lokaal1 = new Lokaal();
        campus.addLokaal(lokaal1);
        campus.removeLokaal(lokaal);
        assertEquals(campus.getAantalLokalen(), 1);
    }


}