package com.management.user.service;

import com.management.user.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
     RoleDto addRole(RoleDto roleDto);
     List<RoleDto> getAllRoles();
     RoleDto getRoleById(String roleId);

}
