package com.campusmanagment.controller;

import com.campusmanagment.dto.create.LokaalCreateDTO;
import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.mapper.CampusMapper;
import com.campusmanagment.mapper.LokaalMapper;
import com.campusmanagment.model.Campus;
import com.campusmanagment.model.Lokaal;
import com.campusmanagment.service.LokaalService;
import com.campusmanagment.service.implemented.CampusServiceImpl;
import com.campusmanagment.service.implemented.LokaalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lokaal")
public class LokaalController {

    private final Logger logger = Logger.getLogger(LokaalController.class.getName());
    private final LokaalServiceImpl lokaalService;
    private final CampusServiceImpl campusService;
    private final LokaalMapper lokaalMapper;
    private final CampusMapper campusMapper;

    @Autowired
    public LokaalController(LokaalServiceImpl lokaalService, LokaalMapper lokaalMapper, CampusServiceImpl campusService, CampusMapper campusMapper) {
        this.lokaalService = lokaalService;
        this.lokaalMapper = lokaalMapper;
        this.campusService = campusService;
        this.campusMapper = campusMapper;
    }

    @GetMapping
    @Operation(summary = "Retrieve all lokalen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of lokalen",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LokaalResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<LokaalResponseDTO>> getAllLokalen() {
        List<Lokaal> lokalen = lokaalService.getAllLokalen();

        List<LokaalResponseDTO> lokaalResponseDTOs = lokalen.stream()
                .map(lokaal -> {
                    CampusResponseDTO campusDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(lokaal.getCampus());
                    return lokaalMapper.lokaalToLokaalResponseDTOWithCampus(lokaal, campusDTO);
                })
                .toList();

        return ResponseEntity.ok(lokaalResponseDTOs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a lokaal by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved lokaal",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LokaalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Lokaal not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LokaalResponseDTO> getLokaalById(@PathVariable Long id) {
        logger.info("Fetching lokaal by id: " + id);

        Lokaal lokaal = lokaalService.getLokaalById(id);
        CampusResponseDTO campusDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(lokaal.getCampus());
        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithCampus(lokaal, campusDTO);

        logger.info("Fetched lokaal by id: " + id);
        return ResponseEntity.ok(lokaalResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new lokaal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created lokaal",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LokaalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Associated campus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LokaalResponseDTO> createLokaal(@Valid @RequestBody LokaalCreateDTO lokaalCreateDTO) {
        Campus campus = campusService.getCampusById(lokaalCreateDTO.getCampusId());
        Lokaal lokaal = lokaalMapper.lokaalCreateDTOToLokaal(lokaalCreateDTO, campus);

        Lokaal savedLokaal = lokaalService.addLokaal(lokaal);

        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithCampus(
                savedLokaal,
                campusMapper.campusToCampusResponseDTOWithoutLokalen(campus));

        return ResponseEntity.ok(lokaalResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing lokaal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated lokaal",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LokaalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Lokaal or associated campus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LokaalResponseDTO> updateLokaal(@PathVariable Long id, @Valid @RequestBody LokaalCreateDTO lokaalCreateDTO) {
        Campus campus = campusService.getCampusById(lokaalCreateDTO.getCampusId());
        Lokaal lokaal = lokaalMapper.lokaalCreateDTOToLokaal(lokaalCreateDTO, campus);

        Lokaal updatedLokaal = lokaalService.updateLokaal(id, lokaal);

        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithCampus(
                updatedLokaal,
                campusMapper.campusToCampusResponseDTOWithoutLokalen(updatedLokaal.getCampus()));

        return ResponseEntity.ok(lokaalResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lokaal", description = "Deletes a lokaal by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted lokaal"),
            @ApiResponse(responseCode = "404", description = "Lokaal not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteLokaal(@PathVariable Long id) {
        lokaalService.deleteLokaal(id);
        return ResponseEntity.noContent().build();
    }
}
