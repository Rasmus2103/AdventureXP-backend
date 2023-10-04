package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.EmployeeRequest;
import com.example.adventurexp.adventure.dto.EmployeeResponse;
import com.example.adventurexp.adventure.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/employee")
public class EmployeeController {
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    List<EmployeeResponse> getEmployees() {
        return employeeService.getEmployees(false);
    }

    @GetMapping(path = "/{username}")
    EmployeeResponse getEmployeeByUsername(@PathVariable String username) {
        return employeeService.findById(username);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EmployeeResponse addCar(@RequestBody EmployeeRequest body) {
        return employeeService.addEmployee(body);
    }

    @PutMapping("/{username}")
    ResponseEntity<Boolean> editMember(@RequestBody EmployeeRequest body, @PathVariable String username){
        return employeeService.editMember(body, username);
    }

    @DeleteMapping("/{username}")
    void deleteEmployeeByUsername(@PathVariable String username) {
        employeeService.deleteEmployeeByUsername(username);
    }

}
