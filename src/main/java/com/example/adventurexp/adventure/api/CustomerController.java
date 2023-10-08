package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.CustomerRequest;
import com.example.adventurexp.adventure.dto.CustomerResponse;
import com.example.adventurexp.adventure.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/customer")
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<CustomerResponse> getCustomers() {
        return customerService.getCustomers(false);
    }

    @GetMapping(path = "/{username}")
    CustomerResponse getCustomerByUsername(@PathVariable String username) {
        return customerService.findById(username);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerResponse addCustomer(@RequestBody CustomerRequest body) {
        return customerService.addCustomer(body);
    }

    @PutMapping(path = "/{username}")
    ResponseEntity<Boolean> editCustomer(@RequestBody CustomerRequest body, @PathVariable String username) {
        return customerService.editCustomer(body, username);
    }

    @DeleteMapping(path="/{username}")
    void deleteCustomer(@PathVariable String username) {
        customerService.deleteCustomer(username);
    }

    @PatchMapping("/addcredit/{username}/{value}")
    ResponseEntity<Boolean> addCredit(@PathVariable String username, @PathVariable int value) {
        return customerService.addCredits(username, value);
    }

}
