package com.emazon.msvc_user.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_user.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.emazon.msvc_user.domain.model.Role;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;

public class RoleAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    public RoleAdapter(IRoleRepository roleRepository, IRoleEntityMapper roleEntityMapper) {
        this.roleRepository = roleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(roleEntityMapper.toEntity(role));
    }

    @Override
    public Role findRoleByName(String name) {
        return roleEntityMapper.toModel(roleRepository.findByName(name).orElse(null));
    }
}
