package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.repository.CustomerRepo;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

}
