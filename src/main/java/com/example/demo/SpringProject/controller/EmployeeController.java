package com.example.demo.SpringProject.controller;


import com.example.demo.SpringProject.dto.EmployeeDTO;
import com.example.demo.SpringProject.service.EmployeeService;
import com.example.demo.SpringProject.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    @Autowired
    @Qualifier("v1")
    private EmployeeService service;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "POST /v1/employees",
            description = "To create a new employees in the database")
    public ResponseEntity<EmployeeDTO> createEmployee(
            @Valid @RequestBody EmployeeDTO employee, @RequestParam(defaultValue = "0") Integer deptId) throws Exception {
        EmployeeDTO createdEmployeeDTO = service.createEmployee(employee, deptId);
        return ResponseEntity.ok().body(createdEmployeeDTO);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "POST /v1/employees",
            description = "To create a batch of new employees in the database")
    public ResponseEntity<List<EmployeeDTO>> batchCreateEmployee(
            @Valid @RequestBody List<EmployeeDTO> employees) {
        return ResponseEntity.ok().body(service.batchCreateEmployee(employees));
    }

    @GetMapping
    @Operation(
            summary = "GET /v1/employees",
            description = "To get all employees present in database using Pagination")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<EmployeeDTO>> getEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "empId") String sortBy,
            @RequestParam(defaultValue = ".") String keyword) {

        return ResponseEntity.ok().body(service.getAllEmployees(pageNo, pageSize, sortBy, keyword));
    }

    @GetMapping("/{id}/{dept}")
    @Operation(
            summary = "GET /v1/employees/{empId,deptId}",
            description = "To get employee with Employee having passed id and department id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeDTO> getEmployee(
            @PathVariable
            @Parameter(name = "id", description = "Please enter employee id to search for", example = "123")
                    Integer id, @PathVariable
            @Parameter(name = "dept", description = "Please enter department id of the employee", example = "123")
                    Integer dept) {
        return ResponseEntity.ok().body(service.getEmployeeById(id, dept));
    }

    @PutMapping("/{id}/{dept}")
    @Operation(
            summary = "PUT /v1/employees/{id}",
            description = "To put employee with EmpId equal to passed id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Integer id,
            @PathVariable
            @Parameter(name = "dept", description = "Please enter department id of the employee", example = "123")
                    Integer dept,
            @RequestBody EmployeeDTO emp) {
        // service call to update
        return ResponseEntity.ok().body(service.updateEmployeeById(id, dept, emp));
    }

    @PatchMapping("/{id}/{dept}")
    @Operation(
            summary = "PATCH /v1/employees/{id}",
            description = "To Update employee with EmpId equal to passed id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeDTO> patchEmployee(
            @PathVariable Integer id,
            @PathVariable
            @Parameter(name = "dept", description = "Please enter department id of the employee", example = "123")
                    Integer dept,
            @RequestBody EmployeeDTO emp) {
        // service call to patch
        return ResponseEntity.ok().body(service.patchEmployeeById(id, dept, emp));
    }

    @DeleteMapping("/{id}/{dept}")
    @Operation(
            summary = "DELETE /v1/employees/{id}",
            description = "To delete employee with EmpId equal to passed id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Message> deleteEmployee(@PathVariable Integer id, @PathVariable
    @Parameter(name = "dept", description = "Please enter department id of the employee", example = "123")
            Integer dept) {

        return ResponseEntity.ok().body(service.deleteEmployeeById(id, dept));
    }
}