package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.EmployeeRequest;
import com.example.adventurexp.adventure.dto.EmployeeResponse;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeResponse> getMembersV2(boolean includeAll, boolean includeReservations) {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeResponse> response = new ArrayList<>();
        for(Employee employee: employees){
          EmployeeResponse er = new EmployeeResponse(employee,includeAll);
            response.add(er);
        }
        return response;
    }

    public List<EmployeeResponse> getEmployees(boolean includeAll) {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeResponse> response = new ArrayList<>();
        for(Employee employee : employees){
            EmployeeResponse er = new EmployeeResponse(employee,includeAll);
            response.add(er);
        }
        return response;
    }

    public EmployeeResponse findById(String username) {
        Employee employee = getEmployeeByUsername(username);
        return new EmployeeResponse(employee,true);
    }

    public EmployeeResponse addEmployee(EmployeeRequest body) {
       if(employeeRepo.existsById(body.getUsername())){
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"This user already exists");
        }
       Employee newEmployee = EmployeeRequest.getEmployeeEntity(body);
       newEmployee = employeeRepo.save(newEmployee);
       return new EmployeeResponse(newEmployee,true);
    }

    public void editEmployee(EmployeeRequest body, String username) {
        Employee employee = getEmployeeByUsername(username);
        if(!body.getUsername().equals(username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot change username");
        }
        employee.setFirstName(body.getFirstName());
        employee.setLastName(body.getLastName());
        employee.setPhoneNumber(body.getPhoneNumber());
        employee.setAddress(body.getAddress());
        employeeRepo.save(employee);
    }

    public void deleteEmployeeByUsername(String username) {
        Employee employee = getEmployeeByUsername(username);
        employeeRepo.delete(employee);
    }

    private Employee getEmployeeByUsername(String username){
        return employeeRepo.findById(username).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Member with this username does not exist"));
    }
}
