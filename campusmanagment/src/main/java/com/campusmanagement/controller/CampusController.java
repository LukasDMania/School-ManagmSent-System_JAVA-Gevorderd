package com.campusmanagement.controller;

import com.campusmanagement.dto.create.CampusCreateDTO;
import com.campusmanagement.dto.response.CampusResponseDTO;
import com.campusmanagement.dto.response.LokaalResponseDTO;
import com.campusmanagement.mapper.CampusMapper;
import com.campusmanagement.mapper.LokaalMapper;
import com.campusmanagement.model.Campus;
import com.campusmanagement.service.implemented.CampusServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/campus")
@Tag(name = "Campus Management", description = "Endpoints for managing campus CRUD and information")
public class CampusController {

    private final CampusServiceImpl campusService;
    private final CampusMapper campusMapper;
    private final LokaalMapper lokaalMapper;

    private static final Logger LOGGER = Logger.getLogger(CampusController.class.getName());

    @Autowired
    public CampusController(CampusServiceImpl campusService, CampusMapper campusMapper, LokaalMapper lokaalMapper) {
        this.campusService = campusService;
        this.campusMapper = campusMapper;
        this.lokaalMapper = lokaalMapper;
    }

    @GetMapping
    @Operation(summary = "Retrieve all campussen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of campuses",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CampusResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CampusResponseDTO>> getAllCampussen() {
        return ResponseEntity.ok(campusService.getAllCampussen()
                .stream()
                .map(campusMapper::campusToCampusResponseDTOWithoutLokalen)
                .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a campus by id(campusNaam)", description = "Returns a campus with nested related Lokalen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved campus",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CampusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Campus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CampusResponseDTO> getCampusById(@PathVariable String id) {
        LOGGER.info("Fetching campus by id: " + id);

        Campus campus = campusService.getCampusById(id);
        List<LokaalResponseDTO> lokalenResponseDTOs = campus.getLokalen()
                .stream()
                .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                .toList();

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithLokalen(campus, lokalenResponseDTOs);

        LOGGER.info("Fetched campus by id: " + id);
        return ResponseEntity.ok(campusResponseDTO);
    }


    @PostMapping
    @Operation(summary = "Create a new campus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created campus",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CampusResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CampusResponseDTO> createCampus(@Valid @RequestBody CampusCreateDTO campusCreateDTO) {
        Campus campusToCreate = campusMapper.campusCreateDTOToCampus(campusCreateDTO);

        Campus createdCampus = campusService.addCampus(campusToCreate);

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(createdCampus);

        return ResponseEntity.ok(campusResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing campus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated campus",
                    content = @Content(mediaType  = "application/json",
                    schema = @Schema(implementation = CampusResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Campus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CampusResponseDTO> updateCampus(@PathVariable String id, @Valid @RequestBody CampusCreateDTO campusCreateDTO){
        Campus campus = campusMapper.campusCreateDTOToCampus(campusCreateDTO);

        Campus updatedCampus = campusService.updateCampus(id, campus);

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(updatedCampus);

        return ResponseEntity.ok(campusResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a campus", description = "Deletes a campus by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted campus"),
            @ApiResponse(responseCode = "404", description = "Campus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteCampus(@PathVariable String id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }
}
