package com.example.EmployeeCrud.service;

import com.example.EmployeeCrud.dto.EmployeeDto;
import com.example.EmployeeCrud.model.EmployeeModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilityConvertServiceImpl implements UtilityConvertService {

    //method to convert model object to dto object
    @Override
    public EmployeeDto convertToDto(EmployeeModel employeeModel) {
            //creating new EmployeeDto object
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmpId(employeeModel.getEmpId());
            employeeDto.setFirstName(employeeModel.getFirstName());
            employeeDto.setLastName(employeeModel.getLastName());
            employeeDto.setAddress(employeeModel.getAddress());
            employeeDto.setCity(employeeModel.getCity());
            employeeDto.setPincode(employeeModel.getPincode());
            return employeeDto;
    }

    //method to convert dto object to model object
    @Override
    public EmployeeModel convertToModel(EmployeeDto employeeDto) {
        //creating new EmployeeModel Object
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName(employeeDto.getFirstName());
        employeeModel.setLastName(employeeDto.getLastName());
        employeeModel.setAddress(employeeDto.getAddress());
        employeeModel.setCity(employeeDto.getCity());
        employeeModel.setPincode(employeeDto.getPincode());
        return employeeModel;

    }

    //checking for validation
    @Override
    public List<String> validation(EmployeeDto employeeDto){
        List<String> errorList=new ArrayList<>();
        if(StringUtils.isBlank(employeeDto.getFirstName()))
            errorList.add("firstName can't be blank");
        if(StringUtils.isBlank(employeeDto.getLastName()))
            errorList.add("lastName can't be blank");
        if(StringUtils.isBlank(employeeDto.getAddress()))
            errorList.add("address can't be blank");
        if(StringUtils.isBlank(employeeDto.getCity()))
            errorList.add("city can't be blank");
        if(StringUtils.isBlank(employeeDto.getPincode()))
            errorList.add("pincode can't be blank");
        return errorList;
    }
}
