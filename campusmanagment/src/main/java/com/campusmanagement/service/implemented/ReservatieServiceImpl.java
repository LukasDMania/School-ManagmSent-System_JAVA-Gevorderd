package com.campusmanagement.service.implemented;

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
            throw new RuntimeException("Failed to fetch reservations", e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while fetching reservations: " + e.getMessage());
            throw new RuntimeException("Failed to fetch reservations", e);
        }
    }

    @Override
    public Reservatie getReservatieById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        try {
            return reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation with ID " + id + " not found"));
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Couldn't fetch reservation: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching reservation with ID " + id);
            throw new RuntimeException("Failed to fetch reservation with ID " + id, e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while fetching reservation with ID " + id);
            throw new RuntimeException("Failed to fetch reservation with ID " + id, e);
        }
    }

    @Override
    @Transactional
    public Reservatie addReservatie(Reservatie reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        validateReservationTimes(reservation);
        validateLokaalAvailability(reservation);

        try {
            return reservationRepository.save(reservation);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Adding reservation failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while adding reservation: " + e.getMessage());
            throw new RuntimeException("Failed to add reservation due to a database error", e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while adding reservation: " + e.getMessage());
            throw new RuntimeException("Failed to add reservation due to an unexpected error", e);
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
            throw new IllegalArgumentException("Reservation or ReservationId cannot be null or under 0");
        }

        try {
            Reservatie existingReservatie = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation with ID " + id + " not found"));

            existingReservatie.setStartTijdstip(reservation.getStartTijdstip());
            existingReservatie.setEindTijdstip(reservation.getEindTijdstip());
            existingReservatie.setUserCommentaar(reservation.getUserCommentaar());
            existingReservatie.setMaxCapaciteit(reservation.getMaxCapaciteit());
            existingReservatie.setLokalen(reservation.getLokalen());
            existingReservatie.setUser(reservation.getUser());


            return reservationRepository.save(existingReservatie);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Reservation update failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("A Database issue while updating reservation with ID:  " + reservation.getId());
            throw new RuntimeException("Failed to update reservation with ID " + reservation.getId(), e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while updating reservation with ID " + reservation.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to update reservation with ID " + reservation.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteReservatie(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        try {
            if (!reservationRepository.existsById(id)) {
                throw new IllegalArgumentException("Reservation with ID " + id + " does not exist");
            }

            reservationRepository.deleteById(id);
            LOGGER.info("Reservation with ID " + id + " deleted successfully");

        } catch (IllegalArgumentException e) {
            LOGGER.warning("Reservation deletion failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("A database error occurred while deleting reservation with ID " + id);
            throw new RuntimeException("Failed to delete reservation with ID " + id, e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while deleting reservation with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete reservation with ID " + id, e);
        }
    }
}

