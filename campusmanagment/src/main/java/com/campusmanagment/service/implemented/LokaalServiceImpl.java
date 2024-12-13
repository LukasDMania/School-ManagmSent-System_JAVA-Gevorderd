package com.campusmanagment.service.implemented;

import com.campusmanagment.model.Lokaal;
import com.campusmanagment.repository.CampusRepository;
import com.campusmanagment.repository.LokaalRepository;
import com.campusmanagment.service.LokaalService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class LokaalServiceImpl implements LokaalService {

    // Repositories
    private final LokaalRepository lokaalRepository;

    // Logger
    private final Logger logger = Logger.getLogger(LokaalServiceImpl.class.getName());

    public LokaalServiceImpl(LokaalRepository lokaalRepository, CampusRepository campusRepository, CampusServiceImpl campusService) {
        this.lokaalRepository = lokaalRepository;
    }

    @Override
    public List<Lokaal> getAllLokalen() {
        try {
            List<Lokaal> lokalen = lokaalRepository.findAll();

            if (lokalen.isEmpty()) {
                logger.info("No lokalen found in the database.");
            }

            return lokalen;
        } catch (DataAccessException e) {
            logger.severe("An error occurred while fetching lokalen");
            throw new RuntimeException("Failed to fetch lokalen", e);
        } catch (Exception e) {
            logger.severe("An unexpected error occurred while fetching lokalen: " + e.getMessage());
            throw new RuntimeException("Failed to fetch lokalen", e);
        }
    }


    @Override
    public Lokaal getLokaalById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Lokaal ID cannot be null");
        }

        try {
            return lokaalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Lokaal with ID " + id + " not found"));
        } catch (IllegalArgumentException e) {
            logger.warning("Couldn't fetch lokaal: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while fetching lokaal with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch lokaal with ID " + id, e);
        } catch (Exception e) {
            logger.severe("An unexpected error occurred while fetching lokaal with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch lokaal with ID " + id, e);
        }
    }

    @Override
    public Lokaal getLokaalByNaam(String lokaalNaam) {
        if (lokaalNaam == null) {
            throw new IllegalArgumentException("Lokaal ID cannot be null");
        }

        try {
            return lokaalRepository.findByLokaalNaam(lokaalNaam)
                    .orElseThrow(() -> new IllegalArgumentException("Lokaal with ID " + lokaalNaam + " not found"));
        } catch (IllegalArgumentException e) {
            logger.warning("Couldn't fetch lokaal: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while fetching lokaal with ID " + lokaalNaam + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch lokaal with ID " + lokaalNaam, e);
        } catch (Exception e) {
            logger.severe("An unexpected error occurred while fetching lokaal with ID " + lokaalNaam + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch lokaal with ID " + lokaalNaam, e);
        }
    }


    @Override
    @Transactional
    public Lokaal addLokaal(Lokaal lokaal) {
        if (lokaal == null) {
            throw new IllegalArgumentException("Lokaal cannot be null");
        }

        if (lokaalRepository.existsByLokaalNaamAndCampus(lokaal.getLokaalNaam(), lokaal.getCampus())) {
            throw new IllegalArgumentException("A Lokaal with this name already exists in the specified campus");
        }

        try {
            return lokaalRepository.save(lokaal);
        } catch (IllegalArgumentException e) {
            logger.warning("Adding lokaal failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while adding lokaal: " + e.getMessage());
            throw new RuntimeException("Failed to add lokaal due to a database error", e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while adding lokaal: " + e.getMessage());
            throw new RuntimeException("Failed to add lokaal due to an unexpected error", e);
        }
    }

    @Override
    @Transactional
    public Lokaal updateLokaal(Long id, Lokaal lokaal) {
        if (id == null || lokaal == null || id <= 0) {
            throw new IllegalArgumentException("Lokaal or LokaalId cannot be null or under 0");
        }

        try {
            Lokaal existingLokaal = lokaalRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Lokaal with ID " + id + " not found"));

            existingLokaal.setLokaalNaam(lokaal.getLokaalNaam());
            existingLokaal.setCapaciteit(lokaal.getCapaciteit());
            existingLokaal.setLokaalType(lokaal.getLokaalType());
            existingLokaal.setVerdieping(lokaal.getVerdieping());
            existingLokaal.setCampus(lokaal.getCampus());

            return lokaalRepository.save(existingLokaal);
        } catch (IllegalArgumentException e) {
            logger.warning("Lokaal update failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database issue while updating lokaal with ID:  " + lokaal.getId());
            throw new RuntimeException("Failed to update lokaal with ID " + lokaal.getId(), e);
        } catch (Exception e) {
            logger.severe("An error occurred while updating lokaal with ID " + lokaal.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to update lokaal with ID " + lokaal.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteLokaal(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Lokaal ID cannot be null");
        }

        try {
            if (!lokaalRepository.existsById(id)) {
                throw new IllegalArgumentException("Lokaal with ID " + id + " does not exist");
            }

            lokaalRepository.deleteById(id);
            logger.info("Lokaal with ID " + id + " deleted successfully");

        } catch (IllegalArgumentException e) {
            logger.warning("Lokaal deletion failedD " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("A database error occurred while deleting lokaal with ID " + id);
            throw new RuntimeException("Failed to delete lokaal with ID " + id, e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while deleting lokaal with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete lokaal with ID " + id, e);
        }
    }
}
