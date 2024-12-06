package com.campusmanagment.service;

import com.campusmanagment.model.Reservatie;

import java.util.List;

public interface ReservatieService {
    List<Reservatie> getAllReservaties();
    Reservatie getReservatieById(Long id);
    Reservatie addReservatie(Reservatie reservatie);
    Reservatie updateReservatie(Reservatie reservatie);
    void deleteReservatie(Long id);
}
