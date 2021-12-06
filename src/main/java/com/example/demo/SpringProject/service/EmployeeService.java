package com.example.demo.SpringProject.service;


import com.example.demo.SpringProject.dto.EmployeeDTO;
import com.example.demo.SpringProject.util.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
  Page<EmployeeDTO> getAllEmployees(
          Integer pageNo, Integer pageSize, String sortBy, String keyword);

  EmployeeDTO createEmployee(EmployeeDTO employeeEntity, Integer deptId) throws Exception;

  EmployeeDTO getEmployeeById(Integer id,Integer dept);

  EmployeeDTO updateEmployeeById(Integer id,Integer dept, EmployeeDTO employeeEntity);

  EmployeeDTO patchEmployeeById(Integer id,Integer dept, EmployeeDTO employeeEntity);

  List<EmployeeDTO> batchCreateEmployee(List<EmployeeDTO> employeeDTOS);

  Message deleteEmployeeById(Integer id, Integer dept);
}