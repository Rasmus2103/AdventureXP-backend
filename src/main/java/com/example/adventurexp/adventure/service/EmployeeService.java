package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.EmployeeRequest;
import com.example.adventurexp.adventure.dto.EmployeeResponse;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.security.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeResponse> getEmployees(boolean includeAll) {
        List<Employee> employees = employeeRepo.findAll();

        return employees.stream()
                .map(member -> new EmployeeResponse(member, includeAll))
                .collect(Collectors.toList());
    }

    public EmployeeResponse addEmployee(EmployeeRequest body) {
        if(employeeRepo.existsById(body.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This employee already exists");
        }

        Employee newEmployee = EmployeeRequest.getEmployeeEntity(body);

        newEmployee.addRole(Role.EMPLOYEE);
        newEmployee = employeeRepo.save(newEmployee);
        return new EmployeeResponse(newEmployee, true);
    }

    public ResponseEntity<Boolean> editMember(EmployeeRequest body, String username) {
        Employee employee = getEmployeeByUsername(username);
        if(!body.getUsername().equals(username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot change username");
        }
        employee.setEmail(body.getEmail());
        employee.setFirstName(body.getFirstName());
        employee.setLastName(body.getLastName());
        employee.setAddress(body.getAddress());
        employee.setPhoneNumber(body.getPhoneNumber());
        employeeRepo.save(employee);
        return ResponseEntity.ok(true);
    }

    public EmployeeResponse findById(String username) {
        Employee employee = getEmployeeByUsername(username);
        return new EmployeeResponse(employee, true);
    }

    public void deleteEmployeeByUsername(String username) {
        Employee member = getEmployeeByUsername(username);
        employeeRepo.delete(member);
    }

    private Employee getEmployeeByUsername(String username) {
        return employeeRepo.findById(username).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"employee with this username does not exist"));
    }

}
