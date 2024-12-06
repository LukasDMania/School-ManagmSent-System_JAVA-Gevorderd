package com.campusmanagment.controller;

import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.mapper.CampusMapper;
import com.campusmanagment.mapper.LokaalMapper;
import com.campusmanagment.model.Campus;
import com.campusmanagment.service.CampusService;
import com.campusmanagment.service.implemented.CampusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/campus")
public class CampusController {

    private final CampusServiceImpl campusService;
    private final CampusMapper campusMapper;
    private final LokaalMapper lokaalMapper;

    @Autowired
    public CampusController(CampusServiceImpl campusService, CampusMapper campusMapper, LokaalMapper lokaalMapper) {
        this.campusService = campusService;
        this.campusMapper = campusMapper;
        this.lokaalMapper = lokaalMapper;
    }


    @GetMapping("/{id}")
    public CampusResponseDTO getCampusById(@PathVariable String id) {
        Campus campus = campusService.getCampusById(id);

        List<LokaalResponseDTO> lokalenDTOs = campus.getLokalen()
                .stream()
                .map(lokaalMapper::lokaalToLokaalResponseDTOWithoutCampusAndReservatie)
                .collect(Collectors.toList());

        return campusMapper.campusToCampusResponseDTOWithLokalen(campus, lokalenDTOs);
    }
}
