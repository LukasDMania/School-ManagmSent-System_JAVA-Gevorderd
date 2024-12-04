package com.campusmanagment.mapper;

import com.campusmanagment.dto.create.UserCreateDTO;
import com.campusmanagment.dto.response.ReservatieResponseDTO;
import com.campusmanagment.dto.response.UserResponseDTO;
import com.campusmanagment.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping from User Entity to UserResponseDTO
    @Mapping(target = "reservatieResponseDTOs", source = "reservaties")
    UserResponseDTO userToUserResponseDTO(User user, List<ReservatieResponseDTO> reservaties);

    // Mapping from UserCreateDTO to User Entity
    User userCreateDTOToUser(UserCreateDTO userCreateDTO);
}
