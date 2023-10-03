package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
}
