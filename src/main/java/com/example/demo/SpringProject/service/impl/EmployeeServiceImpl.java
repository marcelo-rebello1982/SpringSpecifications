package com.example.demo.SpringProject.service.impl;


import com.example.demo.SpringProject.dto.EmployeeDTO;
import com.example.demo.SpringProject.exception.EmployeeConflictException;
import com.example.demo.SpringProject.mapper.EmployeeMapper;
import com.example.demo.SpringProject.model.AddressEntity;
import com.example.demo.SpringProject.model.EmployeeEntity;
import com.example.demo.SpringProject.model.EmployeeKey;
import com.example.demo.SpringProject.repository.AddressRepository;
import com.example.demo.SpringProject.repository.EmployeeRepository;
import com.example.demo.SpringProject.service.EmployeeService;
import com.example.demo.SpringProject.specs.EmployeeSpecification;
import com.example.demo.SpringProject.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("v1")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeMapper mapper;

    public Page<EmployeeDTO> getAllEmployees(
            Integer pageNo, Integer pageSize, String sortBy, String keyword) {
        if (sortBy.equals("empId")) {
            sortBy = "employeeKey.empId";
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));

        Page<EmployeeEntity> result =
                repository.findAll(
                        Specification.where(EmployeeSpecification.likeOperation("empFirstName", keyword))
                                .or(EmployeeSpecification.likeOperation("empLastName", keyword))
                                .or(EmployeeSpecification.ContainsOperation("email", keyword))
                                .or(EmployeeSpecification.likeOperation("employeeKey.deptId", keyword))
                                .or(EmployeeSpecification.likeOperation("employeeKey.empId", keyword))
                                .or(EmployeeSpecification.likeOperation("addressEntity.pinCode", keyword)),
                        paging);

        Page<EmployeeDTO> pageResult = result.map(employeeEntity -> mapper.convertToEmployeeDTO(employeeEntity));
        return pageResult;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee, Integer deptId) throws Exception {

        Optional<EmployeeEntity> employeeEntityList = repository.findByEmail(employee.getEmail());

        if (employeeEntityList.isPresent()) {
            throw new EmployeeConflictException("ATENÇÃO : email cadastrado para ".concat(employeeEntityList.get().getEmpFirstName()), "400");

        } else {
            employeeEntityList = repository.findByContactNumber(employee.getEmpContactNumber());
            if (employeeEntityList.isPresent()) {
                throw new EmployeeConflictException("ATENÇÃO : telefone cadastrado para ".concat(employeeEntityList.get().getEmpFirstName()), "400");
            } else {
                EmployeeKey employeeKey = new EmployeeKey(employee.getEmpId(), deptId);
                Optional<EmployeeEntity> employeeEntity = repository.findByEmployeeKey(employeeKey);
                if (employeeEntity.isPresent()) {
                    throw new EmployeeConflictException("Conflicting Details Passed", "409");
                }
            }
        }
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setPinCode(employee.getPinCode());
        addressEntity.setCity(employee.getCity());
        addressEntity.setState(employee.getState());
        addressEntity.setCountry(employee.getCountry());
        addressRepository.save(addressEntity);
        EmployeeEntity employeeEntity = (mapper.convertToEmployeeEntity(employee));
        employeeEntity.setAddressEntity(addressEntity);
        employeeEntity.getEmployeeKey().setDeptId(deptId);

        return mapper.convertToEmployeeDTO(repository.save(employeeEntity));
    }

    public EmployeeDTO getEmployeeById(Integer id, Integer dept) {
        EmployeeKey employeeKey = new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        Optional<EmployeeEntity> employee = repository.findByEmployeeKey(employeeKey);
        if (employee.isPresent()) return mapper.convertToEmployeeDTO(employee.get());
        else throw new EmployeeConflictException("No Employee with this Id Found", "404");
    }

    public EmployeeDTO updateEmployeeById(Integer id, Integer dept, EmployeeDTO employee) {
        EmployeeKey employeeKey = new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        Optional<EmployeeEntity> emp = repository.findByEmployeeKey(employeeKey);
        if (emp.isPresent()) {
            repository.save(mapper.convertToEmployeeEntity(employee));
            return employee;
        } else {
            throw new EmployeeConflictException("No Employee found to update", "404");
        }
    }

    public EmployeeDTO patchEmployeeById(Integer id, Integer dept, EmployeeDTO employeeDTO) {
        EmployeeKey employeeKey = new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        Optional<EmployeeEntity> emp = repository.findByEmployeeKey(employeeKey);
        if (emp.isPresent()) {
            EmployeeEntity employeeEntity = emp.get();
            if (employeeDTO.getEmail() != null) employeeEntity.setEmail(employeeDTO.getEmail());
            if (employeeDTO.getEmpContactNumber() != null)
                employeeEntity.setEmpContactNumber(employeeDTO.getEmpContactNumber());
            if (employeeDTO.getEmpFirstName() != null)
                employeeEntity.setEmpFirstName(employeeDTO.getEmpFirstName());
            if (employeeDTO.getEmpLastName() != null)
                employeeEntity.setEmpLastName(employeeDTO.getEmpLastName());
            if (employeeDTO.getCity() != null)
                employeeEntity.getAddressEntity().setCity(employeeDTO.getCity());
            if (employeeDTO.getState() != null)
                employeeEntity.getAddressEntity().setState(employeeDTO.getState());
            if (employeeDTO.getCountry() != null)
                employeeEntity.getAddressEntity().setCountry(employeeDTO.getCountry());
            repository.save(employeeEntity);
            return mapper.convertToEmployeeDTO(employeeEntity);
        } else {
            throw new EmployeeConflictException("No Employee found to update", "404");
        }
    }

    public Message deleteEmployeeById(Integer id, Integer dept) {
        EmployeeKey employeeKey = new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        if (repository.existsByEmployeeKey(employeeKey)) {
            repository.deleteByEmployeeKey(employeeKey);
        }
        return new Message("Successful", "Employee Deleted", "200");
    }

    public EmployeeEntity searchEmployeeByEmail(List<EmployeeEntity> employee, String emailToFind) throws Exception {
        return employee.stream().filter(employeeEntity -> employeeEntity
                        .getEmail().equals(emailToFind))
                .findAny().orElseThrow(() -> new Exception("No Employess found"));

    }

    public List<EmployeeDTO> batchCreateEmployee(@Valid List<EmployeeDTO> employeeDTOS) {
        List<EmployeeDTO> empDTOs = new ArrayList<>();
        repository
                .saveAll(
                        employeeDTOS.stream()
                                .map(employeeDTO -> mapper.convertToEmployeeEntity(employeeDTO))
                                .collect(Collectors.toList()))
                .forEach(employeeEntity -> empDTOs.add(mapper.convertToEmployeeDTO(employeeEntity)));
        return empDTOs;
    }
}