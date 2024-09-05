package com.thi.realestateplatformsprojectbe.services.role;

import com.thi.realestateplatformsprojectbe.models.DTO.GetNumberOfRole;
import com.thi.realestateplatformsprojectbe.models.Role;

import java.util.Optional;

public interface IRoleService {
    Iterable<GetNumberOfRole> getAllNumberOfRole();
    Role findByName(String name);
    Iterable<Role> getAllRoles();
    Optional<Role> findById(Long id);

}
