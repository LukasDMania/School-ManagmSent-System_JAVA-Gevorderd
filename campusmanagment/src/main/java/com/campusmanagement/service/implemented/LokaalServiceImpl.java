package com.campusmanagement.service.implemented;

import com.campusmanagement.exception.lokaal.InvalidLokaalDataException;
import com.campusmanagement.exception.lokaal.LokaalNotFoundException;
import com.campusmanagement.exception.lokaal.LokaalOperationException;
import com.campusmanagement.model.Lokaal;
import com.campusmanagement.repository.CampusRepository;
import com.campusmanagement.repository.LokaalRepository;
import com.campusmanagement.service.LokaalService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class LokaalServiceImpl implements LokaalService {
    private final LokaalRepository lokaalRepository;

    private static final Logger LOGGER = Logger.getLogger(LokaalServiceImpl.class.getName());

    public LokaalServiceImpl(LokaalRepository lokaalRepository, CampusRepository campusRepository, CampusServiceImpl campusService) {
        this.lokaalRepository = lokaalRepository;
    }

    @Override
    public List<Lokaal> getAllLokalen() {
        try {
            List<Lokaal> lokalen = lokaalRepository.findAll();

            if (lokalen.isEmpty()) {
                LOGGER.info("No lokalen found in the database.");
            }

            return lokalen;
        } catch (DataAccessException e) {
            LOGGER.severe("An error occurred while fetching lokalen");
            throw new LokaalOperationException("Failed to fetch lokalen", e);
        }
    }


    @Override
    public Lokaal getLokaalById(Long id) {
        if (id == null) {
            throw new InvalidLokaalDataException("Lokaal ID cannot be null");
        }

        try {
            return lokaalRepository.findById(id)
                    .orElseThrow(() -> new LokaalNotFoundException("Lokaal with ID " + id + " not found"));
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching lokaal with ID " + id + ": " + e.getMessage());
            throw new LokaalOperationException("Failed to fetch lokaal with ID " + id, e);
        }
    }

    @Override
    public Lokaal getLokaalByNaam(String lokaalNaam) {
        if (lokaalNaam == null) {
            throw new InvalidLokaalDataException("Lokaal ID cannot be null");
        }

        try {
            return lokaalRepository.findByLokaalNaam(lokaalNaam)
                    .orElseThrow(() -> new LokaalNotFoundException("Lokaal with ID " + lokaalNaam + " not found"));
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching lokaal with ID " + lokaalNaam + ": " + e.getMessage());
            throw new LokaalOperationException("Failed to fetch lokaal with ID " + lokaalNaam, e);
        }
    }


    @Override
    @Transactional
    public Lokaal addLokaal(Lokaal lokaal) {
        if (lokaal == null) {
            throw new InvalidLokaalDataException("Lokaal cannot be null");
        }

        if (lokaalRepository.existsByLokaalNaamAndCampus(lokaal.getLokaalNaam(), lokaal.getCampus())) {
            throw new InvalidLokaalDataException("A Lokaal with this name already exists in the specified campus");
        }

        try {
            return lokaalRepository.save(lokaal);
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while adding lokaal: " + e.getMessage());
            throw new LokaalOperationException("Failed to add lokaal due to a database error", e);
        }
    }

    @Override
    @Transactional
    public Lokaal updateLokaal(Long id, Lokaal lokaal) {
        if (id == null || lokaal == null || id <= 0) {
            throw new InvalidLokaalDataException("Lokaal or LokaalId cannot be null or under 0");
        }

        try {
            Lokaal existingLokaal = lokaalRepository.findById(id)
                    .orElseThrow(() -> new LokaalNotFoundException("Lokaal with ID " + id + " not found"));

            existingLokaal.setLokaalNaam(lokaal.getLokaalNaam());
            existingLokaal.setCapaciteit(lokaal.getCapaciteit());
            existingLokaal.setLokaalType(lokaal.getLokaalType());
            existingLokaal.setVerdieping(lokaal.getVerdieping());
            existingLokaal.setCampus(lokaal.getCampus());

            return lokaalRepository.save(existingLokaal);
        } catch (DataAccessException e) {
            LOGGER.severe("Database issue while updating lokaal with ID:  " + lokaal.getId());
            throw new LokaalOperationException("Failed to update lokaal with ID " + lokaal.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteLokaal(Long id) {
        if (id == null) {
            throw new InvalidLokaalDataException("Lokaal ID cannot be null");
        }

        try {
            if (!lokaalRepository.existsById(id)) {
                throw new LokaalNotFoundException("Lokaal with ID " + id + " does not exist");
            }

            lokaalRepository.deleteById(id);
            LOGGER.info("Lokaal with ID " + id + " deleted successfully");

        } catch (DataAccessException e) {
            LOGGER.severe("A database error occurred while deleting lokaal with ID " + id);
            throw new LokaalOperationException("Failed to delete lokaal with ID " + id, e);
        }
    }
}
