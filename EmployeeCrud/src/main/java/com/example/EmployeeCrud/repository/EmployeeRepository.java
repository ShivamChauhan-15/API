package com.example.EmployeeCrud.repository;

import com.example.EmployeeCrud.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeModel,Long> {
}
