package com.icmon.module.auth.infrastructure.repository.impl;

import com.icmon.module.auth.infrastructure.entity.RoleEntity;
import com.icmon.module.auth.infrastructure.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl {
    private final RoleRepository roleRepository;

    public RoleEntity save(RoleEntity entity) {
        return roleRepository.save(entity);
    }

    public Optional<RoleEntity> findById(UUID id) {
        return roleRepository.findById(id);
    }

    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public void deleteById(UUID id) {
        roleRepository.deleteById(id);
    }
}
