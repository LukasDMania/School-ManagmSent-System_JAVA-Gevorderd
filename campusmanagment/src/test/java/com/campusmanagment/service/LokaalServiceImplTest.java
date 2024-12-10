package com.campusmanagment.service;

import com.campusmanagment.model.Lokaal;
import com.campusmanagment.repository.LokaalRepository;
import com.campusmanagment.service.implemented.LokaalServiceImpl;
import com.campusmanagment.util.LokaalType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LokaalServiceImplTest {

    @Mock
    private LokaalRepository lokaalRepository;

    @InjectMocks
    private LokaalServiceImpl lokaalService;

    private Lokaal lokaal;

    @BeforeEach
    void setUp() {
        lokaal = new Lokaal();
        lokaal.setId(1L);
        lokaal.setLokaalNaam("TestLokaal");
        lokaal.setLokaalType(LokaalType.CAFETARIA);
        lokaal.setCapaciteit(100);
        lokaal.setVerdieping(0);
    }

    @Test
    void testGetAllLokalen_Success() {
        List<Lokaal> expectedLokalen = Arrays.asList(lokaal);
        when(lokaalRepository.findAll()).thenReturn(expectedLokalen);

        List<Lokaal> actualLokalen = lokaalService.getAllLokalen();

        assertNotNull(actualLokalen);
        assertEquals(actualLokalen.size(), 1);
        assertEquals(actualLokalen.getFirst(), lokaal);

        verify(lokaalRepository, times(1)).findAll();
    }

    @Test
    void testGetLokaalById_Success(){
        when(lokaalRepository.findById(1L)).thenReturn(Optional.of(lokaal));

        Lokaal actualLokaal = lokaalService.getLokaalById(1L);

        assertNotNull(actualLokaal);
        assertEquals(actualLokaal.getId(), lokaal.getId());
        assertEquals(actualLokaal.getLokaalType(), lokaal.getLokaalType());

        verify(lokaalRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLokaalById_NotFound(){
        when(lokaalRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                lokaalService.getLokaalById(2L));
    }

    @Test
    void testAddLokaal_Success(){
        when(lokaalRepository.save(lokaal)).thenReturn(lokaal);

        Lokaal savedLokaal = lokaalService.addLokaal(lokaal);

        assertNotNull(savedLokaal);
        assertEquals(lokaal, savedLokaal);

        verify(lokaalRepository, times(1)).save(lokaal);
    }

    @Test
    void testAddLokaal_Null(){
        assertThrows(IllegalArgumentException.class, () -> {
            lokaalService.addLokaal(null);
        });
    }

    @Test
    void testUpdateLokaal_Success(){
        Lokaal existingLokaal = new Lokaal();
        existingLokaal.setId(5L);
        existingLokaal.setLokaalNaam("lokaalNaam");
        existingLokaal.setLokaalType(LokaalType.CAFETARIA);
        existingLokaal.setCapaciteit(50);
        existingLokaal.setVerdieping(0);

        Lokaal updatedLokaal = new Lokaal();
        updatedLokaal.setId(5L);
        updatedLokaal.setLokaalNaam("lokaalNaamUpdate");
        updatedLokaal.setLokaalType(LokaalType.KLASLOKAAL);
        updatedLokaal.setCapaciteit(500);
        updatedLokaal.setVerdieping(1);

        when(lokaalRepository.findById(5L)).thenReturn(Optional.of(existingLokaal));
        when(lokaalRepository.save(existingLokaal)).thenReturn(existingLokaal);

        Lokaal resultLokaal = lokaalService.updateLokaal(5L, updatedLokaal);

        assertNotNull(resultLokaal);
        assertEquals("lokaalNaamUpdate", updatedLokaal.getLokaalNaam());
        assertEquals(LokaalType.KLASLOKAAL, updatedLokaal.getLokaalType());
        assertEquals(500, updatedLokaal.getCapaciteit());
        assertEquals(1, resultLokaal.getVerdieping());

        verify(lokaalRepository, times(1)).findById(5L);
        verify(lokaalRepository, times(1)).save(existingLokaal);



        lokaalService.updateLokaal(5L, updatedLokaal);
    }

    @Test
    void testDeleteLokaal_Success(){
        when(lokaalRepository.existsById(1L)).thenReturn(true);

        lokaalService.deleteLokaal(1L);

        verify(lokaalRepository, times(1)).existsById(1L);
        verify(lokaalRepository, times(1)).deleteById(1L);
    }
}