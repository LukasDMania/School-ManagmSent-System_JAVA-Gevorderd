package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.LokaalCreateDTO;
import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import com.campusmanagment.model.Campus;
import com.campusmanagment.model.Lokaal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LokaalMapper {

    LokaalMapper INSTANCE = Mappers.getMapper(LokaalMapper.class);

    // Mapping from Lokaal Entity to LokaalResponseDTO WITH nested Campus en Reservatie Object
    @Mapping(target = "campusResponseDTO", source = "campus")
    @Mapping(target = "reservatieResponseDTOs", source = "reservaties")
    LokaalResponseDTO lokaalToLokaalResponseDTOWithCampusAndReservatie(Lokaal lokaal, CampusResponseDTO campus, ReservatieResponseDTO reservaties);

    // Mapping from Lokaal Entity to lokaalResponseDTO WITHOUT nested Campus but WITH nested Reservatie Object
    @Mapping(target = "campusResponseDTO", ignore = true)
    @Mapping(target = "reservatieResponseDTOs", source = "reservaties")
    LokaalResponseDTO lokaalToLokaalResponseDTOWithReservatie(Lokaal lokaal, ReservatieResponseDTO reservaties);

    // Mapping from Lokaal Entity to LokaalResponseDTO WITHOUT nested reference objects
    @Mapping(target = "campusResponseDTO", ignore = true)
    @Mapping(target = "reservatieResponseDTOs", ignore = true)
    LokaalResponseDTO lokaalToLokaalResponseDTOWithoutCampusAndReservatie(Lokaal lokaal);


    // Mapping from CreateLokaalDTO to Lokaal Entity
    @Mapping(target = "campus", source = "campus")
    Lokaal lokaalCreateDTOToLokaal (LokaalCreateDTO createLokaalDTO, Campus campus);
}
