package com.example.demo.SpringProject.mapper;


import com.example.demo.SpringProject.dto.EmployeeDTO;
import com.example.demo.SpringProject.model.AddressEntity;
import com.example.demo.SpringProject.model.EmployeeEntity;
import com.example.demo.SpringProject.model.EmployeeKey;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {


    public EmployeeDTO convertToEmployeeDTO(EmployeeEntity employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setEmpContactNumber(employee.getEmpContactNumber());
        employeeDTO.setEmpFirstName(employee.getEmpFirstName());
        employeeDTO.setEmpLastName(employee.getEmpLastName());
        AddressEntity addressEntity;
        addressEntity = employee.getAddressEntity();
        EmployeeKey employeeKey = employee.getEmployeeKey();
        employeeDTO.setEmpId(employeeKey.getEmpId());
        employeeDTO.setCity(addressEntity.getCity());
        employeeDTO.setCountry(addressEntity.getCountry());
        employeeDTO.setState(addressEntity.getState());
        employeeDTO.setPinCode(addressEntity.getPinCode());
        return employeeDTO;
    }

    public EmployeeEntity convertToEmployeeEntity(EmployeeDTO employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmail(employeeDto.getEmail());
        EmployeeKey employeeKey = new EmployeeKey();
        employeeKey.setEmpId(employeeDto.getEmpId());
        employeeEntity.setEmployeeKey(employeeKey);
        employeeEntity.setEmpContactNumber(employeeDto.getEmpContactNumber());
        employeeEntity.setEmpFirstName(employeeDto.getEmpFirstName());
        employeeEntity.setEmpLastName(employeeDto.getEmpLastName());
        return employeeEntity;
    }
}