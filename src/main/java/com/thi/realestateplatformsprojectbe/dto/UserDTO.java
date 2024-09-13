package com.thi.realestateplatformsprojectbe.dto;

import com.thi.realestateplatformsprojectbe.models.IUser;
import com.thi.realestateplatformsprojectbe.models.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Set<Role> roles;
    private IUser user;
}
