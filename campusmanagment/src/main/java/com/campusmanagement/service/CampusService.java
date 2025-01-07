package com.campusmanagement.service;

import com.campusmanagement.model.Campus;

import java.util.List;

public interface CampusService {
    List<Campus> getAllCampussen();
    Campus getCampusById(String id);
    Campus addCampus(Campus campus);
    Campus updateCampus(String id, Campus campus);
    void deleteCampus(String id);
}
