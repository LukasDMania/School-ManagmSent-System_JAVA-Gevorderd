package com.campusmanagment.service;

import com.campusmanagment.dto.response.UserResponseDTO;
import com.campusmanagment.model.User;
import com.campusmanagment.repository.UserRepository;
import com.campusmanagment.service.implemented.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setNaam("naam");
        user.setVoornaam("voornaam");
        user.setGeboorteDatum(LocalDate.now());
        user.setEmail("email");
    }

    @Test
    void testGetAllUsers_Success(){
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> userListResult = userService.getAllUsers();

        assertNotNull(userListResult);
        assertEquals(1, userListResult.size());
        assertEquals(user, userListResult.getFirst());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User userResult = userService.getUserById(1L);

        assertNotNull(userResult);
        assertEquals("naam", userResult.getNaam());
        assertEquals("voornaam", userResult.getVoornaam());
        assertEquals(user.getGeboorteDatum(), userResult.getGeboorteDatum());
        assertEquals("email", userResult.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                userService.getUserById(1L));
    }

    @Test
    void testAddUser_Success(){
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("naam", savedUser.getNaam());
        assertEquals("voornaam", savedUser.getVoornaam());
        assertEquals(user.getGeboorteDatum(), savedUser.getGeboorteDatum());
        assertEquals("email", savedUser.getEmail());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddUser_Null(){
        assertThrows(IllegalArgumentException.class, () ->
                userService.addUser(null));
    }

    @Test
    void testUpdateUser_Success(){
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setNaam("naam");
        existingUser.setVoornaam("voornaam");
        existingUser.setGeboorteDatum(LocalDate.now());
        existingUser.setEmail("email");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setNaam("naamUpdate");
        updatedUser.setVoornaam("voornaamUpdate");
        updatedUser.setGeboorteDatum(LocalDate.now());
        updatedUser.setEmail("emailUpdate");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUserResult = userService.updateUser(1L, updatedUser);

        assertNotNull(updatedUserResult);
        assertEquals("naamUpdate", updatedUserResult.getNaam());
        assertEquals("voornaamUpdate", updatedUserResult.getVoornaam());
        assertEquals("emailUpdate", updatedUserResult.getEmail());
    }

    @Test
    void testDeleteUser_Success(){
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}