package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class employeeRepositoryTest {

    @Autowired
    EmployeeRepo employeeRepo;
    boolean isInitialized = false;

    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            employeeRepo.save(new Employee("f1", "l1", "p1", "a1", "u1", "p1", "e1"));
            employeeRepo.save(new Employee("f2", "l2", "p2", "a2", "u2", "p2", "e2"));
            isInitialized = true;
        }
    }
}
