package com.campusmanagment.controller;

import com.campusmanagment.dto.create.CampusCreateDTO;
import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.mapper.CampusMapper;
import com.campusmanagment.mapper.LokaalMapper;
import com.campusmanagment.model.Campus;
import com.campusmanagment.service.implemented.CampusServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/campus")
public class CampusController {

    private final CampusServiceImpl campusService;
    private final CampusMapper campusMapper;
    private final LokaalMapper lokaalMapper;

    private final Logger logger = Logger.getLogger(CampusController.class.getName());

    @Autowired
    public CampusController(CampusServiceImpl campusService, CampusMapper campusMapper, LokaalMapper lokaalMapper) {
        this.campusService = campusService;
        this.campusMapper = campusMapper;
        this.lokaalMapper = lokaalMapper;
    }

    @GetMapping
    public ResponseEntity<List<CampusResponseDTO>> getAllCampussen() {
        return ResponseEntity.ok(campusService.getAllCampussen()
                .stream()
                .map(campusMapper::campusToCampusResponseDTOWithoutLokalen)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampusResponseDTO> getCampusById(@PathVariable String id) {
        logger.info("Fetching campus by id: " + id);

        Campus campus = campusService.getCampusById(id);
        List<LokaalResponseDTO> lokalenResponseDTOs = campus.getLokalen()
                .stream()
                .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                .toList();

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithLokalen(campus, lokalenResponseDTOs);

        logger.info("Fetched campus by id: " + id);
        return ResponseEntity.ok(campusResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CampusResponseDTO> createCampus(@Valid @RequestBody CampusCreateDTO campusCreateDTO) {
        Campus campusToCreate = campusMapper.campusCreateDTOToCampus(campusCreateDTO);

        Campus createdCampus = campusService.addCampus(campusToCreate);

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(createdCampus);

        return ResponseEntity.ok(campusResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampusResponseDTO> updateCampus(@PathVariable String id, @Valid @RequestBody CampusCreateDTO campusCreateDTO){
        Campus campus = campusMapper.campusCreateDTOToCampus(campusCreateDTO);

        Campus updatedCampus = campusService.updateCampus(id, campus);

        CampusResponseDTO campusResponseDTO = campusMapper.campusToCampusResponseDTOWithoutLokalen(updatedCampus);

        return ResponseEntity.ok(campusResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable String id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }

}
