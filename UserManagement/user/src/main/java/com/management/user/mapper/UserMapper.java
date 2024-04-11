package com.management.user.mapper;

import com.management.user.dto.UserDto;
import com.management.user.models.UserEntity;

public class UserMapper {

    // Convert User JPA Entity into UserDto
    public static UserDto mapToUserDto(UserEntity user){
        UserDto userDto = new UserDto(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash()
        );
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public static UserEntity mapToUser(UserDto userDto){
        UserEntity user = new UserEntity(
                userDto.getType(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPasswordHash()
        );
        return user;
    }
}