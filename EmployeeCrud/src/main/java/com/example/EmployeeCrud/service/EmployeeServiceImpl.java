package com.example.EmployeeCrud.service;

import com.example.EmployeeCrud.dto.EmployeeDto;
import com.example.EmployeeCrud.model.EmployeeModel;
import com.example.EmployeeCrud.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UtilityConvertService utilityService;

    //create employee
    @Override
    public EmployeeDto saveEmployee(EmployeeDto empDto) {
        return utilityService.convertToDto(employeeRepository.save(utilityService.convertToModel(empDto)));
    }

    //to fetch list of all employees
    @Override
    public List<EmployeeDto> fetchAllEmployees() {
        List<EmployeeModel> allEmployees = employeeRepository.findAll();
        if(allEmployees.isEmpty())
            return null;
        return allEmployees.stream()
                    .map(employeeModel -> utilityService.convertToDto(employeeModel))
                    .collect(Collectors.toList());
    }

    //to fetch particular employee
    @Override
    public EmployeeDto getEmployeeById(Long id) {
        try{
            return employeeRepository.findById(id)
                    .map(employeeModel -> utilityService.convertToDto(employeeModel))
                    .orElse(null);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    //to update partial details of employee by id using patch
    public Object updateEmployeePartially(Long id, EmployeeDto employeeDto) {
        Optional<EmployeeModel> employeeModelOptional = employeeRepository.findById(id);
        if(employeeModelOptional.isPresent()){
            boolean updated=false;
            List<String> errorList=new ArrayList<>();
            EmployeeModel employeeModel=employeeModelOptional.get();
            if(employeeDto.getFirstName()!=null) {
                if (StringUtils.isNotBlank(employeeDto.getFirstName()))
                    employeeModel.setFirstName(employeeDto.getFirstName());
                else
                     errorList.add("firstName can't be empty");
            }
            if(employeeDto.getLastName()!=null) {
                if (StringUtils.isNotBlank(employeeDto.getLastName()))
                    employeeModel.setLastName(employeeDto.getLastName());
                else
                    errorList.add("lastName can't be empty");
            }
            if(employeeDto.getAddress()!=null){
                if(StringUtils.isNotBlank(employeeDto.getAddress()))
                    employeeModel.setAddress(employeeDto.getAddress());
                else
                    errorList.add("address can't be empty");
            }
            if(employeeDto.getCity()!=null){
                if(StringUtils.isNotBlank(employeeDto.getCity()))
                    employeeModel.setAddress(employeeDto.getCity());
                else
                    errorList.add("city can't be empty");
            }
            if(employeeDto.getPincode()!=null){
                if(StringUtils.isNotBlank(employeeDto.getPincode()))
                    employeeModel.setPincode(employeeDto.getPincode());
                else
                    errorList.add("pincode can't be empty");
            }
            if(errorList.isEmpty())
                return utilityService.convertToDto(employeeRepository.save(employeeModel));
            return errorList;
        }
        return null;
    }

    //to update employee details by providing entire resource using put
    @Override
    public EmployeeDto updateEmployeeById(Long empId, EmployeeDto empDto) {
        try{
            return employeeRepository.findById(empId)
                    .map(employeeModel -> {
                        //check if user provide all the fields or not
                        if(StringUtils.isAnyBlank(empDto.getFirstName(),empDto.getLastName(),empDto.getAddress(),empDto.getCity(),empDto.getPincode()))
                            return null;  //if any field is empty then return without saving it into database

                        //if no field is empty then set it to database
                        employeeModel.setFirstName(empDto.getFirstName());
                        employeeModel.setLastName(empDto.getLastName());
                        employeeModel.setAddress(empDto.getAddress());
                        employeeModel.setCity(empDto.getCity());
                        employeeModel.setPincode(empDto.getPincode());
                        return utilityService.convertToDto(employeeRepository.save(employeeModel));
                    })
                    .orElseGet(() -> {
                        //if employee is not exist then create new employee
                        EmployeeModel employeeModel = utilityService.convertToModel(empDto);
                        return utilityService.convertToDto(employeeRepository.save(employeeModel));
                    });
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    //to delete particular employee by id
    @Override
    public boolean deleteEmployeeById(Long empId) {
        if (employeeRepository.findById(empId).isPresent()) {
            employeeRepository.deleteById(empId);
            return true;
        }
        return false;
    }


    //to check if employee exists or not
    public boolean checkIfExist(Long empId){
        return employeeRepository.existsById(empId);
    }
}
