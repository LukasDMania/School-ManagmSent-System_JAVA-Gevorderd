package com.campusmanagement.service;

import com.campusmanagement.model.Adres;
import com.campusmanagement.model.Campus;
import com.campusmanagement.repository.CampusRepository;
import com.campusmanagement.service.implemented.CampusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampusServiceImplTest {

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private CampusServiceImpl campusService;

    private Campus testCampus;

    @BeforeEach
    void setUp() {
        testCampus = new Campus();
        testCampus.setCampusNaam("testId");
        testCampus.setAdres(new Adres("Straat", "Stad", "Postcode"));
        testCampus.setParkeerplaatsen(100);
    }

    @Test
    void getAllCampussen_Success() {
        List<Campus> campussen = Arrays.asList(testCampus);
        when(campusRepository.findAll()).thenReturn(campussen);

        List<Campus> actualCampussen = campusService.getAllCampussen();

        assertNotNull(actualCampussen);
        assertEquals(1, actualCampussen.size());
        assertEquals(testCampus, actualCampussen.getFirst());

        verify(campusRepository, times(1)).findAll();
    }

    @Test
    void testGetCampusById_Success() {
        when(campusRepository.findById("testCampus")).thenReturn(Optional.of(testCampus));

        Campus actualCampus = campusService.getCampusById("testCampus");

        assertNotNull(actualCampus);
        assertEquals(testCampus.getCampusNaam(), actualCampus.getCampusNaam());
        assertEquals(testCampus.getAdres(), actualCampus.getAdres());

        verify(campusRepository, times(1)).findById("testCampus");
    }

    @Test
    void testGetCampusById_NotFound() {
        when(campusRepository.findById("emptyCampus")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                campusService.getCampusById("emptyCampus"));
    }

    @Test
    void testAddCampus_Success(){
        when(campusRepository.save(testCampus)).thenReturn(testCampus);

        Campus savedCampus = campusService.addCampus(testCampus);

        assertNotNull(savedCampus);
        assertEquals(testCampus, savedCampus);

        verify(campusRepository, times(1)).save(testCampus);
    }

    @Test
    public void testAddCampus_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            campusService.addCampus(null);
        });
    }

    @Test
    void testUpdateCampus_Success() {
        Campus existingCampus = new Campus();
        existingCampus.setCampusNaam("existingCampus");
        existingCampus.setAdres(new Adres("Straat", "Stad", "Postcode"));
        existingCampus.setParkeerplaatsen(100);

        Campus updatedCampus = new Campus();
        updatedCampus.setCampusNaam("existingCampus");
        updatedCampus.setAdres(new Adres("StraatUpdate", "StadUpdate", "PostcodeUpdate"));
        updatedCampus.setParkeerplaatsen(200);

        when(campusRepository.findById("existingCampus")).thenReturn(Optional.of(existingCampus));
        when(campusRepository.save(existingCampus)).thenReturn(existingCampus);

        Campus resultCampus = campusService.updateCampus("existingCampus", updatedCampus);

        assertNotNull(resultCampus);
        assertEquals(updatedCampus.getAdres(), resultCampus.getAdres());
        assertEquals(updatedCampus.getParkeerplaatsen(), resultCampus.getParkeerplaatsen());

        verify(campusRepository, times(1)).findById("existingCampus");
        verify(campusRepository, times(1)).save(existingCampus);
    }

    @Test
    void testDeleteCampus_Success(){
        when(campusRepository.existsById("testCampus")).thenReturn(true);

        campusService.deleteCampus("testCampus");

        verify(campusRepository, times(1)).existsById("testCampus");
        verify(campusRepository, times(1)).deleteById("testCampus");
    }
}