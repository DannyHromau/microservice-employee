package com.dannyhromau.employee.mapper;

import com.dannyhromau.employee.api.dto.UserDto;
import com.dannyhromau.employee.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User mapToUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto mapToUserDto(User user);

    @Mapping(target = "createdOn", ignore = true)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);

    List<UserDto> mapToListUserDto(List<User> users);
}
