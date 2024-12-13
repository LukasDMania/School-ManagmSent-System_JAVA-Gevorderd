package com.campusmanagment.repository;

import com.campusmanagment.model.Reservatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservatieRepository extends JpaRepository<Reservatie, Long> {

    // Toch Id's toevoegen in alle responseDTO's? dit voelt niet zo robuust
    Optional<Reservatie> findByUserCommentaar(String userCommentaar);
}
