package com.campusmanagment.service;

import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.model.Campus;

import java.util.List;

public interface CampusService {
    List<Campus> getAllCampussen();
    Campus getCampusById(String id);
    Campus addCampus(Campus campus);
    Campus updateCampus(Campus campus);
    void deleteCampus(String id);
}
