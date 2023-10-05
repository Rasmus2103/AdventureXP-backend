package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.CustomerRequest;
import com.example.adventurexp.adventure.dto.CustomerResponse;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.service.CustomerService;
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
public class customerServiceTest {

    @Autowired
    CustomerRepo customerRepo;
    CustomerService customerService;

    Customer c1, c2;

    @BeforeEach
    void setUp() {
    c1 = customerRepo.save(new Customer("f1", "l1", "p1", "a1", "u1", "p1", "e1" ));
    c2 = customerRepo.save(new Customer("f2", "l2", "p2", "a2", "u2", "p2", "e2" ));
    customerService = new CustomerService(customerRepo); //Set up customerService with the mock (H2) database
    }


    @Test
    void testGetCustomersAllDetails() {
        List<CustomerResponse> customerResponses = customerService.getCustomers(true);
        LocalDate time = customerResponses.get(0).getCreated();
        assertNotNull(time, "expects date to be set when true is passed for getcustomers");
    }

    @Test
    void testGetCustomersNoDetails() {
        List<CustomerResponse> customerResponses = customerService.getCustomers(false);
        LocalDate time = customerResponses.get(0).getCreated();
        assertNull(time, "expects date to not be set when false is passed for getcustomers");
    }

    @Test
    void testFindByIdFound() {
        CustomerResponse response = customerService.findById(c1.getUsername());
        assertEquals("f1", response.getFirstName());
    }

    @Test
    void testFindByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> customerService.findById("i dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testAddCustomer_UsernameDoesNotExist() {
        CustomerResponse response = customerService.addCustomer(new CustomerRequest("u3", "p3", "e3", "f3", "l3", "p3", "a3"));
        assertEquals("f3", response.getFirstName());
        assertEquals("u3", response.getUsername());
        assertEquals("e3", response.getEmail());
    }

    @Test
    void testAddCustomer_UsernameDoesExistThrows() {
        CustomerRequest c3 = new CustomerRequest("u1", "p3", "e3", "f3", "l3", "p3", "a3");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> customerService.addCustomer(c3));
        assertEquals(/*HttpStatus.BAD_REQUEST*/ HttpStatusCode.valueOf(400), ex.getStatusCode());
    }

    @Test
    void testEditCustomerWithExistingUsername() {
        assertEquals(ResponseEntity.ok(true), customerService.editCustomer(new CustomerRequest("u2", "p22", "e22", "f22", "l22", "p22", "a22"), "u2"));
    }

    @Test
    void testEditCustomerNON_ExistingUsernameThrows() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> customerService.editCustomer(
                new CustomerRequest("u2", "p22", "e22", "f22", "l22", "p22", "a22"), "u222"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testEditCustomerChangePrimaryKeyThrows() {
        CustomerRequest request = new CustomerRequest(c1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerService.editCustomer(request, "u2"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testDeleteCustomerByUsername() {
        CustomerResponse response = customerService.findById("u1");
        assertEquals("u1", response.getUsername());
        customerService.deleteCustomer("u1");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerService.findById("u1"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testDeleteCustomer_ThatDontExist() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                customerService.deleteCustomer("12222"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }



}
