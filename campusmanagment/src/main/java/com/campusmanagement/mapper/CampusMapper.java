package com.campusmanagement.mapper;

import com.campusmanagement.dto.create.CampusCreateDTO;
import com.campusmanagement.dto.response.CampusResponseDTO;
import com.campusmanagement.dto.response.LokaalResponseDTO;
import com.campusmanagement.model.Campus;
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

    // Mapping from Campus Entity to CampusResponseDTO without nested LokalenDTO Objects
    @Mapping(target = "lokalenResponseDTOs", ignore = true)
    CampusResponseDTO campusToCampusResponseDTOWithoutLokalen(Campus campus);

    // Mapping from CampusCreateDTO to Campus Entity
    Campus campusCreateDTOToCampus(CampusCreateDTO campusCreateDTO);
}
