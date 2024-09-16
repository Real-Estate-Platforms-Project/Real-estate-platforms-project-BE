package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT SUBSTRING(e.code, LOCATE('-', e.code) + 1) FROM Employee e ORDER BY e.code DESC LIMIT 1")
    String findMaxEmployeeCodeNumber();



    @Query("SELECT e FROM Employee e WHERE " +
            "(:code IS NULL OR :code = '' OR e.code = :code) AND " +
            "(:name IS NULL OR :name = '' OR e.name LIKE %:name%) AND " +
            "(:email IS NULL OR :email = '' OR e.email = :email) AND " +
            "(:position IS NULL OR :position = '' OR e.position.name = :position)")
    Iterable<Employee> searchEmployees(@Param("code") String code,
                                       @Param("name") String name,
                                       @Param("email") String email,
                                       @Param("position") String position);


    Employee findByAccountId(Long accountId);


}