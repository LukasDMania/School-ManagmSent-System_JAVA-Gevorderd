package com.campusmanagment.service.implemented;

import com.campusmanagment.model.User;
import com.campusmanagment.repository.UserRepository;
import com.campusmanagment.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                logger.info("No users found in the database.");
            }

            return users;
        } catch (DataAccessException e) {
            logger.severe("A Database error occurred while fetching users");
            throw new RuntimeException("Failed to fetch users", e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while fetching users: " + e.getMessage());
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
        } catch (IllegalArgumentException e) {
            logger.warning("Couldn't fetch user: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while fetching user with ID " + id);
            throw new RuntimeException("Failed to fetch user with ID " + id, e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while fetching user with ID " + id);
            throw new RuntimeException("Failed to fetch user with ID " + id, e);
        }
    }

    @Override
    public User getUserByNaamAndVoornaam(String naam, String voornaam) {
        if (naam == null || voornaam == null) {
            throw new IllegalArgumentException(naam + " and " + voornaam + " cannot be null");
        }

        try {
            return userRepository.getUserByNaamAndVoornaam(naam, voornaam)
                    .orElseThrow(() -> new IllegalArgumentException("User with naam " + naam + " not found"));
        } catch (IllegalArgumentException e) {
            logger.warning("Couldn't fetch user: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database error occurred while fetching user with naam " + naam);
            throw new RuntimeException("Failed to fetch user with ID " + naam, e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while fetching user with naam " + naam);
            throw new RuntimeException("Failed to fetch user with ID " + naam, e);
        }
    }

    @Override
    @Transactional
    public User addUser(User user) {
        if (user == null || user.getNaam() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            logger.warning("Adding user failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("A Database error occurred while adding user: " + e.getMessage());
            throw new RuntimeException("Failed to add user due to a database error", e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while adding user: " + e.getMessage());
            throw new RuntimeException("Failed to add user due to an unexpected error", e);
        }
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        if (user == null || id == null || id <= 0) {
            throw new IllegalArgumentException("User or UserId cannot be null or under 0");
        }

        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));

            existingUser.setNaam(user.getNaam());
            existingUser.setVoornaam(user.getVoornaam());
            existingUser.setGeboorteDatum(user.getGeboorteDatum());
            existingUser.setEmail(user.getEmail());
            existingUser.setReservaties(user.getReservaties());

            return userRepository.save(existingUser);
        } catch (IllegalArgumentException e) {
            logger.warning("User update failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("Database issue while updating user with ID:  " + user.getId());
            throw new RuntimeException("Failed to update user with ID " + user.getId(), e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while updating user with ID " + user.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to update user with ID " + user.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        try {
            if (!userRepository.existsById(id)) {
                throw new IllegalArgumentException("User with ID " + id + " does not exist");
            }

            userRepository.deleteById(id);
            logger.info("User with ID " + id + " deleted successfully");

        } catch (IllegalArgumentException e) {
            logger.warning("User deletion failed: " + e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.severe("A database error occurred while deleting user with ID " + id);
            throw new RuntimeException("Failed to delete user with ID " + id, e);
        } catch (Exception e) {
            logger.severe("An uncaught error occurred while deleting user with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete user with ID " + id, e);
        }
    }
}
