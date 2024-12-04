package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.ReservatieCreateDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.campusmanagment.model.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservatieMapper {

    ReservatieMapper INSTANCE = Mappers.getMapper(ReservatieMapper.class);

    // Mapping from Reservatie Entity to ReservatieResponseDTO with nested User Object and List of Lokaal Objects
    @Mapping(target = "userResponseDTO", source = "user")
    @Mapping(target = "lokalenResponseDTOs", source = "lokalen")
    ReservatieResponseDTO reservatieToReservatieResponseDTOWithUserAndLokalen(Reservatie reservatie, User user, List<Lokaal> lokalen);

    // Mapping from ReservatieCreateDTO to Reservatie Entity
    @Mapping(target = "user", source = "user")
    @Mapping(target = "lokalen", source = "lokalen")
    Reservatie reservatieCreateDTOToReservatie(ReservatieCreateDTO reservatieCreateDTO, User user, List<Lokaal> lokalen);
}
