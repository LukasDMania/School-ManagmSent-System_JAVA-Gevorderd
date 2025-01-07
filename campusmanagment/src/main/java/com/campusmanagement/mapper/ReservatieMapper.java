package com.campusmanagement.mapper;

import com.campusmanagement.dto.create.ReservatieCreateDTO;
import com.campusmanagement.dto.response.LokaalResponseDTO;
import com.campusmanagement.dto.response.ReservatieResponseDTO;
import com.campusmanagement.dto.response.UserResponseDTO;
import com.campusmanagement.model.Lokaal;
import com.campusmanagement.model.Reservatie;
import com.campusmanagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservatieMapper {

    ReservatieMapper INSTANCE = Mappers.getMapper(ReservatieMapper.class);

    // Mapping from Reservatie Entity to ReservatieResponseDTO with nested User Object and List of Lokaal Objects
    @Mapping(target = "userResponseDTO", source = "user")
    @Mapping(target = "lokalenResponseDTOs", source = "lokalen")
    ReservatieResponseDTO reservatieToReservatieResponseDTOWithUserAndLokalen(Reservatie reservatie, UserResponseDTO user, List<LokaalResponseDTO> lokalen);

    // Mapping from ReservatieCreateDTO to Reservatie Entity
    @Mapping(target = "user", source = "user")
    @Mapping(target = "lokalen", source = "lokalen")
    Reservatie reservatieCreateDTOToReservatie(ReservatieCreateDTO reservatieCreateDTO, User user, List<Lokaal> lokalen);

    // Mapping from Reservatie Entity to ReservatieResponseDTO with nested User Object but WITHOUT nested Lokaal Objects
    @Mapping(target = "userResponseDTO", source = "user")
    @Mapping(target = "lokalenResponseDTOs", ignore = true)
    ReservatieResponseDTO reservatieToReservatieResponseDTOWithUser(Reservatie reservatie, UserResponseDTO user);

    // Mapping from Reservatie Entity to ReservatieResponseDTO WITHOUT nested reference objects
    @Mapping(target = "userResponseDTO", ignore = true)
    @Mapping(target = "lokalenResponseDTOs", ignore = true)
    ReservatieResponseDTO reservatieToReservatieResponseDTOWithoutUserAndLokalen(Reservatie reservatie);

}
