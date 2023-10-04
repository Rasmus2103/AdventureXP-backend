package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,String> {
}
