package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.LokaalCreateDTO;
import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.model.Lokaal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LokaalMapper {

    CampusMapper INSTANCE = Mappers.getMapper(CampusMapper.class);

    // Mapping from Lokaal Entity to LokaalResponseDTO with nested Campus Object
    @Mapping(target = "campusResponseDTO", source = "campus")
    LokaalResponseDTO lokaalToLokaalResponseDTOWithCampus(Lokaal lokaal, CampusResponseDTO campus);

    // Mapping from CreateLokaalDTO to Lokaal Entity
    Lokaal lokaalCreateDTOToLokaal (LokaalCreateDTO createLokaalDTO);
}
