package com.example.EmployeeCrud.service;

import com.example.EmployeeCrud.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto empDto);

    List<EmployeeDto> fetchAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    Object updateEmployeePartially(Long id, EmployeeDto employeeDto);

    EmployeeDto updateEmployeeById(Long id,EmployeeDto empDto);

    boolean deleteEmployeeById(Long id);
    boolean checkIfExist(Long empId);
}
