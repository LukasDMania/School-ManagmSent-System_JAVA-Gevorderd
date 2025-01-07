package com.campusmanagement.mapper;

import com.campusmanagement.dto.create.LokaalCreateDTO;
import com.campusmanagement.dto.response.CampusResponseDTO;
import com.campusmanagement.dto.response.LokaalResponseDTO;
import com.campusmanagement.dto.response.ReservatieResponseDTO;
import com.campusmanagement.model.Campus;
import com.campusmanagement.model.Lokaal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LokaalMapper {

    LokaalMapper INSTANCE = Mappers.getMapper(LokaalMapper.class);

    // Mapping from Lokaal Entity to LokaalResponseDTO WITH nested Campus en Reservatie Object
    @Mapping(target = "campusResponseDTO", source = "campus")
    @Mapping(target = "reservatieResponseDTOs", source = "reservaties")
    LokaalResponseDTO lokaalToLokaalResponseDTOWithCampusAndReservatie(Lokaal lokaal, CampusResponseDTO campus, List<ReservatieResponseDTO> reservaties);

    // Mapping from Lokaal Entity to LokaalResponseDTO WITH nested Campus but WITHOUT nested Reservatie Object
    @Mapping(target = "campusResponseDTO", source = "campus")
    @Mapping(target = "reservatieResponseDTOs", ignore = true)
    LokaalResponseDTO lokaalToLokaalResponseDTOWithCampus(Lokaal lokaal, CampusResponseDTO campus);

    // Mapping from Lokaal Entity to lokaalResponseDTO WITHOUT nested Campus but WITH nested Reservatie Object
    @Mapping(target = "campusResponseDTO", ignore = true)
    @Mapping(target = "reservatieResponseDTOs", source = "reservaties")
    LokaalResponseDTO lokaalToLokaalResponseDTOWithReservatie(Lokaal lokaal, List<ReservatieResponseDTO> reservaties);

    // Mapping from Lokaal Entity to LokaalResponseDTO WITHOUT nested reference objects
    @Mapping(target = "campusResponseDTO", ignore = true)
    @Mapping(target = "reservatieResponseDTOs", ignore = true)
    LokaalResponseDTO lokaalToLokaalResponseDTOWithoutCampusAndReservatie(Lokaal lokaal);


    // Mapping from CreateLokaalDTO to Lokaal Entity
    @Mapping(target = "campus", source = "campus")
    Lokaal lokaalCreateDTOToLokaal (LokaalCreateDTO createLokaalDTO, Campus campus);
}
