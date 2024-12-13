package com.campusmanagment.service;

import com.campusmanagment.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByNaamAndVoornaam(String naam, String voornaam);
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
