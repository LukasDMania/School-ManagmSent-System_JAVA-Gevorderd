package com.campusmanagment.repository;

import com.campusmanagment.model.Campus;
import com.campusmanagment.model.Lokaal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LokaalRepository extends JpaRepository<Lokaal, Long> {
    Optional<Lokaal> findByLokaalNaam(String lokaalNaam);
    Boolean existsByLokaalNaamAndCampus(String lokaalNaam, Campus campus);
}
