package com.campusmanagment.service.implemented;

import com.campusmanagment.model.Reservatie;
import com.campusmanagment.repository.ReservatieRepository;
import com.campusmanagment.service.ReservatieService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ReservatieServiceImpl implements ReservatieService {

    private final ReservatieRepository reservationRepository;
    private final Logger logger = Logger.getLogger(ReservatieServiceImpl.class.getName());

    public ReservatieServiceImpl(ReservatieRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservatie> getAllReservaties() {
        try {
            List<Reservatie> reservations = reservationRepository.findAll();

            if (reservations.isEmpty()) {
                logger.info("No reservations found in the database.");
            }

            return reservations;
        } catch (DataAccessException e) {
            logger.severe("A Database error occurred while fetching reservations");
            throw new RuntimeException("Failed to fetch reservations", e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while fetching reservations: " + e.getMessage());
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
            logger.warning("Couldn't fetch reservation: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while fetching reservation with ID " + id);
            throw new RuntimeException("Failed to fetch reservation with ID " + id, e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while fetching reservation with ID " + id);
            throw new RuntimeException("Failed to fetch reservation with ID " + id, e);
        }
    }

    @Override
    @Transactional
    public Reservatie addReservatie(Reservatie reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        try {
            return reservationRepository.save(reservation);
        } catch (IllegalArgumentException e) {
            logger.warning("Adding reservation failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while adding reservation: " + e.getMessage());
            throw new RuntimeException("Failed to add reservation due to a database error", e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while adding reservation: " + e.getMessage());
            throw new RuntimeException("Failed to add reservation due to an unexpected error", e);
        }
    }

    @Override
    @Transactional
    public Reservatie updateReservatie(Reservatie reservation) {
        if (reservation == null || reservation.getId() == null || reservation.getId() <= 0) {
            throw new IllegalArgumentException("Reservation or ReservationId cannot be null or under 0");
        }

        try {
            Reservatie existingReservatie = reservationRepository.findById(reservation.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Reservation with ID " + reservation.getId() + " not found"));

            return reservationRepository.save(existingReservatie);
        } catch (IllegalArgumentException e) {
            logger.warning("Reservation update failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("A Database issue while updating reservation with ID:  " + reservation.getId());
            throw new RuntimeException("Failed to update reservation with ID " + reservation.getId(), e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while updating reservation with ID " + reservation.getId() + ": " + e.getMessage());
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
            logger.info("Reservation with ID " + id + " deleted successfully");

        } catch (IllegalArgumentException e) {
            logger.warning("Reservation deletion failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("A database error occurred while deleting reservation with ID " + id);
            throw new RuntimeException("Failed to delete reservation with ID " + id, e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while deleting reservation with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete reservation with ID " + id, e);
        }
    }
}

