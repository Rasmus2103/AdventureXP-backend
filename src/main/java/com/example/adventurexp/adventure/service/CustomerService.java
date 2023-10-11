package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.*;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.security.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<CustomerResponse> getCustomers(boolean includeAll) {
        List<Customer> customers = customerRepo.findAll();

        return customers.stream()
                .map(customer -> new CustomerResponse(customer, includeAll))
                .collect(Collectors.toList());
    }

    public CustomerResponse addCustomer(CustomerRequest body) {
        if(customerRepo.existsById(body.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This username already exists");
        }

        Customer newCustomer = CustomerRequest.getCustomerEntity(body);
        newCustomer.addRole(Role.USER);
        newCustomer = customerRepo.save(newCustomer);
        return new CustomerResponse(newCustomer, true);
    }

    public ResponseEntity<Boolean> editCustomer(CustomerRequest body, String username) {
        Customer customer = getCustomerByUsername(username);
        if(!body.getUsername().equals(username)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot change username");
        }
        customer.setEmail(body.getEmail());
        customer.setFirstName(body.getFirstName());
        customer.setLastName(body.getLastName());
        customer.setAddress(body.getAddress());
        customer.setPhoneNumber(body.getPhoneNumber());
        customerRepo.save(customer);
        return ResponseEntity.ok(true);
    }

    public void deleteCustomer(String username) {
        Customer customer = getCustomerByUsername(username);
        customerRepo.delete(customer);
    }

    public CustomerResponse findById(String username) {
        Customer customer = getCustomerByUsername(username);
        return new CustomerResponse(customer, true);
    }

    public ResponseEntity<Boolean> addCredits(String username, double credits){
        Customer customer = getCustomerByUsername(username);
        customer.setCredit(customer.getCredit() + credits);
        customerRepo.save(customer);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> removeCredits(String username, double credits){
        Customer customer = getCustomerByUsername(username);
        customer.setCredit(customer.getCredit() - credits);
        customerRepo.save(customer);
        return ResponseEntity.ok(true);
    }

    private Customer getCustomerByUsername(String username) {
        return customerRepo.findById(username).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Member with this username does not exist"));
    }

}
