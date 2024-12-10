package com.campusmanagment.controller;

import com.campusmanagment.dto.create.ReservatieCreateDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import com.campusmanagment.dto.response.UserResponseDTO;
import com.campusmanagment.mapper.LokaalMapper;
import com.campusmanagment.mapper.ReservatieMapper;
import com.campusmanagment.mapper.UserMapper;
import com.campusmanagment.model.Lokaal;
import com.campusmanagment.model.Reservatie;
import com.campusmanagment.model.User;
import com.campusmanagment.service.implemented.LokaalServiceImpl;
import com.campusmanagment.service.implemented.ReservatieServiceImpl;
import com.campusmanagment.service.implemented.UserServiceImpl;
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
    public ResponseEntity<Void> deleteReservatie(@PathVariable Long id) {
        reservatieService.deleteReservatie(id);
        return ResponseEntity.noContent().build();
    }
}
