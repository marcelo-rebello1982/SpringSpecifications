package com.example.demo.SpringProject.repository;


import com.example.demo.SpringProject.model.EmployeeEntity;
import com.example.demo.SpringProject.model.EmployeeKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, EmployeeKey> {

    @Query("select e from EmployeeEntity e where e.email = :email")
    Optional<EmployeeEntity> findByEmail(@Param("email") String email);

    @Query("select e from EmployeeEntity e where e.empContactNumber = :number")
    Optional<EmployeeEntity> findByContactNumber(@Param("number") String number);

    @Query("select e from EmployeeEntity e where e.employeeKey = :employeeKey")
    Optional<EmployeeEntity> findByEmployeeKey(@Param("employeeKey") EmployeeKey employeeKey);

    @Query("select (count(e) > 0) from EmployeeEntity e where e.employeeKey = ?1")
    Boolean existsByEmployeeKey(EmployeeKey employeeKey);

    @Modifying
    @Query("delete from EmployeeEntity e where e.employeeKey = :employeeKey")
    void deleteByEmployeeKey(@Param("employeeKey") EmployeeKey employeeKey);

    Page<EmployeeEntity> findAll(Specification<EmployeeEntity> spec, Pageable pageable);

    List<EmployeeEntity> findAll();
}