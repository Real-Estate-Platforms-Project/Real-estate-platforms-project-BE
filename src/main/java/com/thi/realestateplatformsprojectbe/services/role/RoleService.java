package com.thi.realestateplatformsprojectbe.services.role;

import com.thi.realestateplatformsprojectbe.dto.GetNumberOfRole;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Iterable<GetNumberOfRole> getAllNumberOfRole() {
        return roleRepository.getAllNumberOfRole();
    }
}
