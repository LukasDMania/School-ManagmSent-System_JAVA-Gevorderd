package com.campusmanagment.controller;

import com.campusmanagment.dto.create.UserCreateDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import com.campusmanagment.dto.response.UserResponseDTO;
import com.campusmanagment.mapper.ReservatieMapper;
import com.campusmanagment.mapper.UserMapper;
import com.campusmanagment.model.User;
import com.campusmanagment.service.ReservatieService;
import com.campusmanagment.service.implemented.ReservatieServiceImpl;
import com.campusmanagment.service.implemented.UserServiceImpl;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ReservatieServiceImpl reservatieService;
    private final ReservatieMapper reservatieMapper;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper, ReservatieServiceImpl reservatieService, ReservatieMapper reservatieMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.reservatieService = reservatieService;
        this.reservatieMapper = reservatieMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers()
                .stream()
                .map(userMapper::userToUserResponseDTOWithoutReservaties)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        List<ReservatieResponseDTO> reservaties = user.getReservaties()
                .stream()
                .map(reservatieMapper::reservatieToReservatieResponseDTOWithoutUserAndLokalen)
                .toList();

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithReservaties(user, reservaties);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        User userToCreate = userMapper.userCreateDTOToUser(userCreateDTO);

        User createdUser = userService.addUser(userToCreate);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(createdUser);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO userCreateDTO) {
        User userToUpdate = userMapper.userCreateDTOToUser(userCreateDTO);

        User updatedUser = userService.updateUser(id, userToUpdate);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(updatedUser);

        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
