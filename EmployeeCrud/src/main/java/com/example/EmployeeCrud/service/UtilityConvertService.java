package com.example.EmployeeCrud.service;

import com.example.EmployeeCrud.dto.EmployeeDto;
import com.example.EmployeeCrud.model.EmployeeModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface UtilityConvertService {
    EmployeeDto convertToDto(EmployeeModel employeeModel);
    EmployeeModel convertToModel(EmployeeDto employeeDto);

    List<String> validation(EmployeeDto employeeDto);


}
