package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE " +
            "(:code IS NULL OR e.code = :code) AND " +
            "(:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:email IS NULL OR e.email LIKE %:email%) AND " +
            "(:positionId IS NULL OR e.position.id = :positionId)")
    Iterable<Employee> searchEmployees(@Param("code") String code,
                                       @Param("name") String name,
                                       @Param("email") String email,
                                       @Param("positionId") Long positionId);

    Employee findByAccountId(Long accountId);
}