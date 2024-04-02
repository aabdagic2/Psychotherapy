package com.management.user.service;

import com.management.user.dto.RoleDto;

import java.util.List;

public interface RoleService {
     RoleDto addRole(RoleDto roleDto);
     List<RoleDto> getAllRoles();

}
