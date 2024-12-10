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
    public ResponseEntity<LokaalResponseDTO> getLokaalById(@PathVariable Long id) {
        Lokaal lokaal = lokaalService.getLokaalById(id);
        CampusResponseDTO campusDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(lokaal.getCampus());
        LokaalResponseDTO lokaalResponseDTO = lokaalMapper.lokaalToLokaalResponseDTOWithCampus(lokaal, campusDTO);

        return ResponseEntity.ok(lokaalResponseDTO);
    }

    @PostMapping
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
    public ResponseEntity<Void> deleteLokaal(@PathVariable Long id) {
        lokaalService.deleteLokaal(id);
        return ResponseEntity.noContent().build();
    }
}
