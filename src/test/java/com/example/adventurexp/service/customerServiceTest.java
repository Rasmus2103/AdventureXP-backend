package com.example.adventurexp.service;

import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class customerServiceTest {

    @Autowired
    CustomerRepo customerRepo;
    CustomerService customerService;



    @BeforeEach
    void setUp() {

    }



}
