package com.management.user.mapper;

import com.management.user.dto.UserDto;
import com.management.user.models.RoleEntity;
import com.management.user.models.UserEntity;
import com.management.user.repository.RoleRepository;
import com.management.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private RoleRepository roleRepository;

    // Convert User JPA Entity into UserDto
    public UserDto mapToUserDto(UserEntity user){
        UserDto userDto = new UserDto(
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId(),
                user.getRole().getRoleId()
        );
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public UserEntity mapToUser(UserDto userDto){
        UserEntity user = new UserEntity(
                userDto.getType(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPassword(),
                roleRepository.findById(userDto.getRoleId()).orElse(null)
        );
        return user;
    }
}