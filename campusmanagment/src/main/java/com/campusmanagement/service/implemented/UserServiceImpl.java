package com.campusmanagement.service.implemented;

import com.campusmanagement.exception.user.InvalidUserDataException;
import com.campusmanagement.exception.user.UserNotFoundException;
import com.campusmanagement.exception.user.UserOperationException;
import com.campusmanagement.model.User;
import com.campusmanagement.repository.UserRepository;
import com.campusmanagement.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                LOGGER.info("No users found in the database.");
            }

            return users;
        } catch (DataAccessException e) {
            LOGGER.severe("A Database error occurred while fetching users");
            throw new UserOperationException("Failed to fetch users", e);
        }
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new InvalidUserDataException("User ID cannot be null");
        }

        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching user with ID " + id);
            throw new UserOperationException("Failed to fetch user with ID " + id, e);
        }
    }

    @Override
    public User getUserByNaamAndVoornaam(String naam, String voornaam) {
        if (naam == null || voornaam == null) {
            throw new InvalidUserDataException(naam + " and " + voornaam + " cannot be null");
        }

        try {
            return userRepository.getUserByNaamAndVoornaam(naam, voornaam)
                    .orElseThrow(() -> new UserNotFoundException("User with naam " + naam + " not found"));
        } catch (DataAccessException e) {
            LOGGER.severe("Database error occurred while fetching user with naam " + naam);
            throw new UserOperationException("Failed to fetch user with ID " + naam, e);
        }
    }

    @Override
    @Transactional
    public User addUser(User user) {
        if (user == null || user.getNaam() == null) {
            throw new InvalidUserDataException("User cannot be null");
        }

        try {
            return userRepository.save(user);
        } catch (DataAccessException e) {
            LOGGER.severe("A Database error occurred while adding user: " + e.getMessage());
            throw new UserOperationException("Failed to add user due to a database error", e);
        }
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        if (user == null || id == null || id <= 0) {
            throw new InvalidUserDataException("User or UserId cannot be null or under 0");
        }

        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            existingUser.setNaam(user.getNaam());
            existingUser.setVoornaam(user.getVoornaam());
            existingUser.setGeboorteDatum(user.getGeboorteDatum());
            existingUser.setEmail(user.getEmail());
            existingUser.setReservaties(user.getReservaties());

            return userRepository.save(existingUser);
        } catch (DataAccessException e) {
            LOGGER.severe("Database issue while updating user with ID:  " + user.getId());
            throw new UserOperationException("Failed to update user with ID " + user.getId(), e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (id == null) {
            throw new InvalidUserDataException("User ID cannot be null");
        }

        try {
            if (!userRepository.existsById(id)) {
                throw new UserNotFoundException("User with ID " + id + " does not exist");
            }

            userRepository.deleteById(id);
            LOGGER.info("User with ID " + id + " deleted successfully");
        } catch (DataAccessException e) {
            LOGGER.severe("A database error occurred while deleting user with ID " + id);
            throw new UserOperationException("Failed to delete user with ID " + id, e);
        }
    }
}
