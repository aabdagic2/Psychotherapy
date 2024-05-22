package com.management.user.mapper;

import com.management.user.dto.RoleDto;
import com.management.user.models.RoleEntity;

public class RoleMapper {
    public static RoleDto toRoleDto(RoleEntity roleEntity) {
        return new RoleDto(
                roleEntity.getRoleId(),
                roleEntity.getName()
        );
    }

    public static RoleEntity toRoleEntity(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleDto.getName());
        return roleEntity;
    }
}

