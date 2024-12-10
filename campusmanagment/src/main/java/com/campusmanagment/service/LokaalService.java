package com.campusmanagment.service;
import com.campusmanagment.model.Lokaal;

import java.util.List;

public interface LokaalService {
    List<Lokaal> getAllLokalen();
    Lokaal getLokaalById(Long id);
    Lokaal addLokaal(Lokaal lokaal);
    Lokaal updateLokaal(Long id, Lokaal lokaal);
    void deleteLokaal(Long id);
}
