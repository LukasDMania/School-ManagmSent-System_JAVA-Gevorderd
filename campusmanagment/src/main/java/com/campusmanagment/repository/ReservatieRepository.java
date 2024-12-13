package com.campusmanagment.repository;

import com.campusmanagment.model.Reservatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservatieRepository extends JpaRepository<Reservatie, Long> {

    // Toch Id's toevoegen in alle responseDTO's? dit voelt niet zo robuust
    Optional<Reservatie> findByUserCommentaar(String userCommentaar);

    @Query("SELECT r FROM Reservatie r " +
            "JOIN r.lokalen l " +
            "WHERE l.id = :lokaalId " +
            "AND (" +
            "   (r.startTijdstip < :eindTijdstip AND r.eindTijdstip > :startTijdstip)" +
            ")")
    List<Reservatie> findOverlappingReservations(
            @Param("lokaalId") Long lokaalId,
            @Param("startTijdstip") LocalDateTime startTijdstip,
            @Param("eindTijdstip") LocalDateTime eindTijdstip
    );
}
