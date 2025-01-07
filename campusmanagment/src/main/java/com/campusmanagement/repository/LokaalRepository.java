package com.campusmanagement.repository;

import com.campusmanagement.model.Campus;
import com.campusmanagement.model.Lokaal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LokaalRepository extends JpaRepository<Lokaal, Long> {
    Optional<Lokaal> findByLokaalNaam(String lokaalNaam);
    Boolean existsByLokaalNaamAndCampus(String lokaalNaam, Campus campus);
}
