package com.thi.realestateplatformsprojectbe.repositories;


import com.thi.realestateplatformsprojectbe.models.DTO.GetNumberOfRole;
import com.thi.realestateplatformsprojectbe.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
        @Query(nativeQuery = true, value = "select name, count(account_id) as number from roles left join security1.accounts_roles ar on roles.id = ar.roles_id group by name;\n")

    Iterable<GetNumberOfRole> getAllNumberOfRole();

    Role findByName(String name);
}
