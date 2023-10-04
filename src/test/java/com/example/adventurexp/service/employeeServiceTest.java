package com.example.adventurexp.service;

import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class employeeServiceTest {


    @Autowired
    EmployeeRepo employeeRepo;
    EmployeeService employeeService;


    @BeforeEach
    void setUp() {

    }




}
