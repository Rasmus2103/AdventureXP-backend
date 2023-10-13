package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.CustomerRequest;
import com.example.adventurexp.adventure.dto.CustomerResponse;
import com.example.adventurexp.adventure.dto.EmployeeRequest;
import com.example.adventurexp.adventure.dto.EmployeeResponse;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class employeeServiceTest {


    @Autowired
    EmployeeRepo employeeRepo;
    EmployeeService employeeService;

    Employee e1, e2;

    @BeforeEach
    void setUp() {
        e1 = employeeRepo.save(new Employee("f1", "l1", "p1", "a1", "u1", "p1", "e1" ));
        e2 = employeeRepo.save(new Employee("f2", "l2", "p2", "a2", "u2", "p2", "e2" ));
        employeeService = new EmployeeService(employeeRepo); //Set up employeeService with the mock (H2) database
    }

    @Test
    void testGetEmployeesAllDetails() {
        List<EmployeeResponse> employeeResponses = employeeService.getEmployees(true);
        LocalDate time = employeeResponses.get(0).getCreated();
        assertNotNull(time, "expects date to be set when true is passed for getemployees");
    }

    @Test
    void testGetEmployeesNoDetails() {
        List<EmployeeResponse> employeeResponses = employeeService.getEmployees(false);
        LocalDate time = employeeResponses.get(0).getCreated();
        assertNull(time, "expects date to not be set when false is passed for getemployees");
    }

    @Test
    void testFindByIdFound() {
        EmployeeResponse response = employeeService.findById(e1.getUsername());
        assertNotNull(response.getFirstName());
    }

    @Test
    void testFindByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> employeeService.findById("i dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testAddEmployee_UsernameDoesNotExist(){
        EmployeeResponse response = employeeService.addEmployee(new EmployeeRequest("u3", "p3", "e3", "f3", "l3", "p3", "a3" ));
        assertEquals("f3", response.getFirstName());
        assertEquals("u3", response.getUsername());
        assertEquals("e3", response.getEmail());
    }

    @Test
    void testAddEmployee_UsernameDoesExistThrows() {
        EmployeeRequest e3 = new EmployeeRequest("u1", "p3", "e3", "f3", "l3", "p3", "a3" );
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> employeeService.addEmployee(e3));
        assertEquals(/*HttpStatus.BAD_REQUEST*/ HttpStatusCode.valueOf(400), ex.getStatusCode());
    }

    @Test
    void testEditEmployeeWithExistingUsername(){
        assertEquals(ResponseEntity.ok(true), employeeService.editMember(new EmployeeRequest("u1", "p11", "e11", "f11", "l11", "p11", "a11" ), "u1"));
    }

    @Test
    void testEditCustomerNON_ExistingUsernameThrows() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> employeeService.editMember(
                new EmployeeRequest("u2", "p22", "e22", "f22", "l22", "p22", "a22"), "u222"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testEditCustomerChangePrimaryKeyThrows() {
        EmployeeRequest request = new EmployeeRequest(e1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                employeeService.editMember(request, "u2"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }


    @Test
    void testDeleteCustomerByUsername() {
        EmployeeResponse response = employeeService.findById("u1");
        assertEquals("u1", response.getUsername());
        employeeService.deleteEmployeeByUsername("u1");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                employeeService.findById("u1"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testDeleteCustomer_ThatDontExist() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                employeeService.deleteEmployeeByUsername("12222"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }





}
