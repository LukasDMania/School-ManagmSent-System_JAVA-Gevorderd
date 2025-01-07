package com.campusmanagement.controller;

import com.campusmanagement.dto.create.ReservatieCreateDTO;
import com.campusmanagement.dto.response.LokaalResponseDTO;
import com.campusmanagement.dto.response.ReservatieResponseDTO;
import com.campusmanagement.dto.response.UserResponseDTO;
import com.campusmanagement.mapper.LokaalMapper;
import com.campusmanagement.mapper.ReservatieMapper;
import com.campusmanagement.mapper.UserMapper;
import com.campusmanagement.model.Lokaal;
import com.campusmanagement.model.Reservatie;
import com.campusmanagement.model.User;
import com.campusmanagement.service.implemented.LokaalServiceImpl;
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
@RequestMapping("/reservatie")
public class ReservatieController {

    private final ReservatieServiceImpl reservatieService;
    private final ReservatieMapper reservatieMapper;
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final LokaalServiceImpl lokaalService;
    private final LokaalMapper lokaalMapper;

    @Autowired
    public ReservatieController(
            ReservatieServiceImpl reservatieService,
            ReservatieMapper reservatieMapper,
            UserMapper userMapper,
            LokaalMapper lokaalMapper,
            UserServiceImpl userService1,
            LokaalServiceImpl lokaalService1) {

        this.reservatieService = reservatieService;
        this.reservatieMapper = reservatieMapper;
        this.userMapper = userMapper;
        this.lokaalMapper = lokaalMapper;
        this.userService = userService1;
        this.lokaalService = lokaalService1;
    }

    @GetMapping
    @Operation(summary = "Retrieve all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reservations",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservatieResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ReservatieResponseDTO>> getAllReservaties() {
        List<Reservatie> reservaties = reservatieService.getAllReservaties();

        List<ReservatieResponseDTO> reservatieResponseDTOs = reservaties.stream()
                .map(reservatie -> {
                    UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(reservatie.getUser());
                    List<LokaalResponseDTO> lokaalResponseDTOs = reservatie.getLokalen()
                            .stream()
                            .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                            .toList();
                    return reservatieMapper.reservatieToReservatieResponseDTOWithUserAndLokalen(reservatie, userResponseDTO, lokaalResponseDTOs);
                })
                .toList();

        return ResponseEntity.ok(reservatieResponseDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a reservation by id", description = "Returns a reservation with its relation user and lokalen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservation",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservatieResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReservatieResponseDTO> getReservatieById(@PathVariable Long id){
        Reservatie reservatie = reservatieService.getReservatieById(id);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTOWithoutReservaties(reservatie.getUser());
        List<LokaalResponseDTO> lokaalResponseDTOs = reservatie.getLokalen()
                .stream()
                .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                .toList();

        ReservatieResponseDTO reservatieResponseDTO = reservatieMapper.reservatieToReservatieResponseDTOWithUserAndLokalen(reservatie, userResponseDTO, lokaalResponseDTOs);

        return ResponseEntity.ok(reservatieResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created reservation",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservatieResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User or lokaal not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReservatieResponseDTO> createReservatie(@Valid @RequestBody ReservatieCreateDTO reservatieCreateDTO) {
        User user = userService.getUserById(reservatieCreateDTO.getUserId());
        List<Lokaal> lokalen = reservatieCreateDTO.getLokalenIds()
                .stream()
                .map(lokaalService::getLokaalById)
                .toList();

        Reservatie reservatieToCreate = reservatieMapper.reservatieCreateDTOToReservatie(reservatieCreateDTO, user, lokalen);

        Reservatie createdReservatie = reservatieService.addReservatie(reservatieToCreate);

        ReservatieResponseDTO reservatieResponseDTO = reservatieMapper.reservatieToReservatieResponseDTOWithUserAndLokalen(createdReservatie,
                userMapper.userToUserResponseDTOWithoutReservaties(createdReservatie.getUser()),
                createdReservatie.getLokalen()
                        .stream()
                        .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                        .toList());

        return ResponseEntity.ok(reservatieResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated reservation",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservatieResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Reservation, user, or lokaal not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ReservatieResponseDTO> updateReservatie(@PathVariable Long id, @Valid @RequestBody ReservatieCreateDTO reservatieCreateDTO) {
        Reservatie reservatie = reservatieMapper.reservatieCreateDTOToReservatie(reservatieCreateDTO, userService.getUserById(reservatieCreateDTO.getUserId()),
                reservatieCreateDTO.getLokalenIds()
                        .stream()
                        .map(lokaalService::getLokaalById)
                        .toList());

        Reservatie updatedReservatie = reservatieService.updateReservatie(id, reservatie);

        ReservatieResponseDTO reservatieResponseDTO = reservatieMapper.reservatieToReservatieResponseDTOWithUserAndLokalen(updatedReservatie,
                userMapper.userToUserResponseDTOWithoutReservaties(updatedReservatie.getUser()),
                updatedReservatie.getLokalen()
                        .stream()
                        .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                        .toList());

        return ResponseEntity.ok(reservatieResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservation", description = "Deletes a reservation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteReservatie(@PathVariable Long id) {
        reservatieService.deleteReservatie(id);
        return ResponseEntity.noContent().build();
    }
}
