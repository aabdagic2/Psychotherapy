package com.management.user.service;

import com.management.user.dto.RoleDto;
import com.management.user.exceptions.RoleNotFoundException;
import com.management.user.mapper.RoleMapper;
import com.management.user.models.RoleEntity;
import com.management.user.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto addRole(RoleDto roleDto) {
        String roleName = roleDto.getName();
        if (roleRepository.existsByName(roleName)) {
            throw new IllegalArgumentException("Role with name '" + roleName + "' already exists.");
        }

        RoleEntity roleEntity = new RoleEntity(roleName, UUID.randomUUID().toString());
        RoleEntity savedRole = roleRepository.save(roleEntity);

        return RoleMapper.toRoleDto(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleEntity> roleEntities = (List<RoleEntity>) roleRepository.findAll();
        return roleEntities.stream()
                .map(RoleMapper::toRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(String roleId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return RoleMapper.toRoleDto(role);
    }

}

