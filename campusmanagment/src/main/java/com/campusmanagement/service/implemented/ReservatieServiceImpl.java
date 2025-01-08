package com.campusmanagement.service.implemented;

import com.campusmanagement.exception.reservatie.InvalidReservatieDataException;
import com.campusmanagement.exception.reservatie.ReservatieNotFoundException;
import com.campusmanagement.exception.reservatie.ReservatieOperationException;
import com.campusmanagement.model.Lokaal;
import com.campusmanagement.model.Reservatie;
import com.campusmanagement.repository.ReservatieRepository;
import com.campusmanagement.service.ReservatieService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ReservatieServiceImpl implements ReservatieService {
    private final ReservatieRepository reservationRepository;

    private static final Logger LOGGER = Logger.getLogger(ReservatieServiceImpl.class.getName());

    public ReservatieServiceImpl(ReservatieRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservatie> getAllReservaties() {
        try {
            List<Reservatie> reservations = reservationRepository.findAll();

            if (reservations.isEmpty()) {
                LOGGER.info("No reservations found in the database.");
            }

            return reservations;
        } catch (DataAccessException e) {
            LOGGER.severe("A Database error occurred while fetching reservations");
            throw new ReservatieOperationException("Failed to fetch reservations", e);
        }
    }

    @Override
    public Reservatie getReservatieById(Long id) {
        if (id == null) {
            throw new InvalidReservatieDataException("Reservation ID cannot be null");
        }

        try {
            return reservationRepository.findById(id)
                    .orElseThrow(() -> new ReservatieNotFoundException("Reservation with ID " + id + " not found"));
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching reservation with ID " + id);
            throw new ReservatieOperationException("Failed to fetch reservation with ID " + id, e);
        }
    }

    @Override
    @Transactional
    public Reservatie addReservatie(Reservatie reservation) {
        if (reservation == null) {
            throw new InvalidReservatieDataException("Reservation cannot be null");
        }

        validateReservationTimes(reservation);
        validateLokaalAvailability(reservation);

        try {
            return reservationRepository.save(reservation);
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while adding reservation: " + e.getMessage());
            throw new ReservatieOperationException("Failed to add reservation due to a database error", e);
        }
    }
    private void validateReservationTimes(Reservatie reservation) {
        LocalDateTime now = LocalDateTime.now();

        if (reservation.getStartTijdstip().isAfter(reservation.getEindTijdstip())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (reservation.getEindTijdstip().isBefore(now) || reservation.getStartTijdstip().isBefore(now)) {
            throw new IllegalArgumentException("Reservation end time cannot be in the past");
        }
    }
    private void validateLokaalAvailability(Reservatie reservation) {
        for (Lokaal lokaal : reservation.getLokalen()) {
            List<Reservatie> conflictingReservaties = reservationRepository.findOverlappingReservations(
                    lokaal.getId(),
                    reservation.getStartTijdstip(),
                    reservation.getEindTijdstip()
            );

            if (!conflictingReservaties.isEmpty()) {
                throw new IllegalArgumentException("Lokaal is not available at that time");
            }
        }
    }

    @Override
    @Transactional
    public Reservatie updateReservatie(Long id, Reservatie reservation) {
        if (reservation == null || id == null || id <= 0) {
            throw new InvalidReservatieDataException("Reservation or ReservationId cannot be null or under 0");
        }

        try {
            Reservatie existingReservatie = reservationRepository.findById(id)
                    .orElseThrow(() -> new ReservatieNotFoundException("Reservation with ID " + id + " not found"));

            existingReservatie.setStartTijdstip(reservation.getStartTijdstip());
            existingReservatie.setEindTijdstip(reservation.getEindTijdstip());
            existingReservatie.setUserCommentaar(reservation.getUserCommentaar());
            existingReservatie.setMaxCapaciteit(reservation.getMaxCapaciteit());
            existingReservatie.setLokalen(reservation.getLokalen());
            existingReservatie.setUser(reservation.getUser());


            return reservationRepository.save(existingReservatie);
        } catch (DataAccessException e) {
            LOGGER.severe("A Database issue while updating reservation with ID:  " + reservation.getId());
            throw new ReservatieOperationException("Failed to update reservation with ID " + reservation.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteReservatie(Long id) {
        if (id == null) {
            throw new InvalidReservatieDataException("Reservation ID cannot be null");
        }

        try {
            if (!reservationRepository.existsById(id)) {
                throw new ReservatieNotFoundException("Reservation with ID " + id + " does not exist");
            }

            reservationRepository.deleteById(id);
            LOGGER.info("Reservation with ID " + id + " deleted successfully");
        } catch (DataAccessException e) {
            LOGGER.severe("A database error occurred while deleting reservation with ID " + id);
            throw new ReservatieOperationException("Failed to delete reservation with ID " + id, e);
        }
    }
}

