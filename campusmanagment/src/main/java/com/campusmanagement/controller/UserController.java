package com.campusmanagement.controller;

import com.campusmanagement.dto.create.UserCreateDTO;
import com.campusmanagement.dto.response.ReservatieResponseDTO;
import com.campusmanagement.dto.response.UserResponseDTO;
import com.campusmanagement.mapper.ReservatieMapper;
import com.campusmanagement.mapper.UserMapper;
import com.campusmanagement.model.User;
import com.campusmanagement.service.implemented.ReservatieServiceImpl;
import com.campusmanagement.service.implemented.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ReservatieMapper reservatieMapper;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper, ReservatieMapper reservatieMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.reservatieMapper = reservatieMapper;
    }

    @GetMapping
    @Operation(summary = "Retrieve all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers()
                .stream()
                .map(userMapper::userToUserResponseDTOWithoutReservaties)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a user by id", description = "Returns a user with their reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created user",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User userToCreate = userMapper.userCreateDTOToUser(userCreateDTO);

        User createdUser = userService.addUser(userToCreate);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(createdUser);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO userCreateDTO) {
        User userToUpdate = userMapper.userCreateDTOToUser(userCreateDTO);
        User existingUser = userService.getUserById(id);
        userToUpdate.setReservaties(userService.getUserByNaamAndVoornaam(existingUser.getNaam(), existingUser.getVoornaam()).getReservaties());
        User updatedUser = userService.updateUser(id, userToUpdate);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(updatedUser);

        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a reservation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
