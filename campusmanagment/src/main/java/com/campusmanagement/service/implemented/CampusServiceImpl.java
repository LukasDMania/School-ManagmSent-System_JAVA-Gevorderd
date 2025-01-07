package com.campusmanagement.service.implemented;

import com.campusmanagement.model.Campus;
import com.campusmanagement.repository.CampusRepository;
import com.campusmanagement.service.CampusService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CampusServiceImpl implements CampusService {
    private final CampusRepository campusRepository;

    private static final Logger LOGGER = Logger.getLogger(CampusServiceImpl.class.getName());

    @Autowired
    public CampusServiceImpl(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @Override
    public List<Campus> getAllCampussen() {
        try {
            List<Campus> campuses = campusRepository.findAll();

            if (campuses.isEmpty()) {
                LOGGER.info("No campuses found in the database.");
            }

            return campuses;
        } catch (DataAccessException e) {
            LOGGER.severe("An error occurred while fetching campuses");
            throw new RuntimeException("Failed to fetch campuses", e);
        } catch (Exception e) {
            LOGGER.severe("An unexpected error occurred while fetching campuses: " + e.getMessage());
            throw new RuntimeException("Failed to fetch campuses", e);
        }
    }


    @Override
    public Campus getCampusById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Campus ID cannot be null or empty");
        }

        try {
            return campusRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Campus with ID " + id + " not found"));
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Couldn't fetch campus: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching campus with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch campus with ID " + id, e);
        } catch (Exception e) {
            LOGGER.severe("An unexpected error occurred while fetching campus with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch campus with ID " + id, e);
        }
    }


    @Override
    @Transactional
    public Campus addCampus(Campus campus) {
        if (campus == null) {
            throw new IllegalArgumentException("Campus cannot be null");
        }

        try {
            return campusRepository.save(campus);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Adding campus failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while adding campus: " + e.getMessage());
            throw new RuntimeException("Failed to add campus due to a database error", e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while adding campus: " + e.getMessage());
            throw new RuntimeException("Failed to add campus due to an unexpected error", e);
        }
    }

    @Override
    @Transactional
    public Campus updateCampus(String id, Campus campus) {
        if (campus == null || id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Campus and Id cannot be null or empty");
        }

        try {
            Campus existingCampus = campusRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Campus with ID " + id + " not found"));

            // Update fields
            existingCampus.setCampusNaam(campus.getCampusNaam());
            existingCampus.setAdres(campus.getAdres());
            existingCampus.setParkeerplaatsen(campus.getParkeerplaatsen());

            return campusRepository.save(existingCampus);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Campus update failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("Database issue while updating campus with name: " + campus.getCampusNaam());
            throw new RuntimeException("Failed to update campus due to database issues", e);
        } catch (Exception e) {
            LOGGER.severe("Unexpected error occurred while updating campus with name " + campus.getCampusNaam() + ": " + e.getMessage());
            throw new RuntimeException("Failed to update campus due to an unexpected error", e);
        }
    }

    @Override
    @Transactional
    public void deleteCampus(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Campus ID cannot be null or empty");
        }
        try {
            if (!campusRepository.existsById(id)) {
                throw new IllegalArgumentException("Campus with ID " + id + " not found");
            }

            campusRepository.deleteById(id);
            LOGGER.info("Campus with ID " + id + " deleted successfully");

        } catch (IllegalArgumentException e) {
            LOGGER.warning("Campus deletion failed" + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            LOGGER.severe("Database issue while deleting campus with ID: " + id);
            throw new RuntimeException("Failed to delete campus with ID " + id, e);
        } catch (Exception e) {
            LOGGER.severe("An uncaught error occurred while deleting campus with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete campus with ID " + id, e);
        }
    }
}
