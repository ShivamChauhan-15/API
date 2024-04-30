package com.example.EmployeeCrud.controller;

import com.example.EmployeeCrud.dto.EmployeeDto;
import com.example.EmployeeCrud.service.EmployeeService;
import com.example.EmployeeCrud.service.UtilityConvertService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UtilityConvertService utilityService;

    //create new employee
    @PostMapping("/create")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employee) {
        List<String> errorList = utilityService.validation(employee);
        if (errorList.isEmpty()) {
            EmployeeDto employeeDto = employeeService.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }

    //getting list of all employess
    @GetMapping()
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.fetchAllEmployees();
        if (!employeeDtos.isEmpty())
            return ResponseEntity.ok(employeeDtos);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Employee exist");
    }

    //get particular employee by id
    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
        EmployeeDto employeeById = employeeService.getEmployeeById(id);
        if (employeeById != null)
            return ResponseEntity.ok(employeeById);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee of id " + id + " not exist");
    }

    //update employee details using id
    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) {
        List<String> errorList = utilityService.validation(employeeDto);
        if (errorList.isEmpty()) {
            EmployeeDto updatedEmployee = employeeService.updateEmployeeById(id, employeeDto);
            return ResponseEntity.ok(updatedEmployee);
        } else if (employeeService.checkIfExist(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee of id " + id + " not exist");
    }

    //update partially employee detail using id
    @PatchMapping("{id}")
    public ResponseEntity<?> updateEmp(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) {
        Object updatedEmployee = updatedEmployee = employeeService.updateEmployeePartially(id, employeeDto);
        if (updatedEmployee != null)
            return ResponseEntity.ok(updatedEmployee);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee of id " + id + " not exist");
    }

    //delete particular employee using id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
        boolean result = employeeService.deleteEmployeeById(id);
        if (result) {
            return ResponseEntity.ok().body("Employee of id " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee of id " + id + " not exist");
        }

    }
}
