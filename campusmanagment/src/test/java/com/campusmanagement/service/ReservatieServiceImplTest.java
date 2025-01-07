package com.campusmanagement.service;

import com.campusmanagement.model.Reservatie;
import com.campusmanagement.repository.ReservatieRepository;
import com.campusmanagement.service.implemented.ReservatieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservatieServiceImplTest {

    @Mock
    ReservatieRepository reservatieRepository;

    @InjectMocks
    ReservatieServiceImpl reservatieService;

    private Reservatie reservatie;


    @BeforeEach
    void setUp(){
        reservatie = new Reservatie();
        reservatie.setId(1L);
        reservatie.setStartTijdstip(LocalDateTime.now().plusHours(1L));
        reservatie.setEindTijdstip(LocalDateTime.now().plusHours(2L));
        reservatie.setUserCommentaar("userCommentaar");
    }

    @Test
    void testGetAllReservaties_Success(){
        List<Reservatie> reservaties = Arrays.asList(reservatie);
        when(reservatieRepository.findAll()).thenReturn(reservaties);

        List<Reservatie> reservatiesResult = reservatieService.getAllReservaties();

        assertNotNull(reservatiesResult);
        assertEquals(1, reservatiesResult.size());
        assertEquals(reservatie, reservatiesResult.getFirst());

        verify(reservatieRepository, times(1)).findAll();
    }

    @Test
    void testGetReservatieById_Success(){
        when(reservatieRepository.findById(1L)).thenReturn(Optional.of(reservatie));

        Reservatie reservatieResult = reservatieService.getReservatieById(1L);

        assertNotNull(reservatieResult);
        assertEquals(1L, reservatieResult.getId());
        assertEquals(reservatie.getStartTijdstip(), reservatieResult.getStartTijdstip());
        assertEquals(reservatie.getEindTijdstip(), reservatieResult.getEindTijdstip());
        assertEquals("userCommentaar", reservatieResult.getUserCommentaar());

        verify(reservatieRepository, times(1)).findById(1L);
    }

    @Test
    void testGetReservatieById_NotFound(){
        when(reservatieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reservatieService.getReservatieById(1L));
    }

    @Test
    void testAddReservatie_Success(){
        when(reservatieRepository.save(reservatie)).thenReturn(reservatie);

        Reservatie savedReservatie = reservatieService.addReservatie(reservatie);

        assertNotNull(savedReservatie);
        assertEquals(reservatie, savedReservatie);

        verify(reservatieRepository, times(1)).save(reservatie);
    }

    @Test
    void testAddReservatie_Null(){
        assertThrows(IllegalArgumentException.class, () -> {
            reservatieService.addReservatie(null);
        });
    }

    @Test
    void testUpdateReservatie_Success(){
        Reservatie existingReservatie = new Reservatie();
        existingReservatie.setId(1L);
        existingReservatie.setStartTijdstip(LocalDateTime.now());
        existingReservatie.setEindTijdstip(LocalDateTime.now().plusHours(2L));
        existingReservatie.setUserCommentaar("userCommentaar");

        Reservatie updatedReservatie = new Reservatie();
        updatedReservatie.setId(1L);
        updatedReservatie.setStartTijdstip(LocalDateTime.now().plusHours(1L));
        updatedReservatie.setEindTijdstip(LocalDateTime.now().plusHours(3L));
        updatedReservatie.setUserCommentaar("userCommentaarUpdated");

        when(reservatieRepository.findById(1L)).thenReturn(Optional.of(existingReservatie));
        when(reservatieRepository.save(existingReservatie)).thenReturn(existingReservatie);

        Reservatie reservatieResult = reservatieService.updateReservatie(1L, updatedReservatie);

        assertNotNull(reservatieResult);
        assertEquals(updatedReservatie.getStartTijdstip(), reservatieResult.getStartTijdstip());
        assertEquals(updatedReservatie.getEindTijdstip(), reservatieResult.getEindTijdstip());
        assertEquals("userCommentaarUpdated", reservatieResult.getUserCommentaar());

        verify(reservatieRepository, times(1)).findById(1L);
        verify(reservatieRepository, times(1)).save(existingReservatie);
    }

    @Test
    void testDeleteReservatie_Success(){
        when(reservatieRepository.existsById(1L)).thenReturn(true);

        reservatieService.deleteReservatie(1L);

        verify(reservatieRepository, times(1)).existsById(1L);
        verify(reservatieRepository, times(1)).deleteById(1L);
    }
}