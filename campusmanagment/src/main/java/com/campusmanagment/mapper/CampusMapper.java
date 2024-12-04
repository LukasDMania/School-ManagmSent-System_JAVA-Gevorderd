package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.CampusCreateDTO;
import com.campusmanagment.dto.response.CampusResponseDTO;
import com.campusmanagment.dto.response.LokaalResponseDTO;
import com.campusmanagment.model.Campus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CampusMapper {

    CampusMapper INSTANCE = Mappers.getMapper(CampusMapper.class);

    // Mapping from Campus Entity to CampusResponseDTO with nested LokalenDTO Objects
    @Mapping(target = "lokalenResponseDTOs", source = "lokalen")
    CampusResponseDTO campusToCampusResponseDTOWithLokalen(Campus campus, List<LokaalResponseDTO> lokalen);

    // Mapping from CampusCreateDTO to Campus Entity
    Campus campusCreateDTOToCampus(CampusCreateDTO campusCreateDTO);
}
