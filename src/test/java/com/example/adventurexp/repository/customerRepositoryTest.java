package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class customerRepositoryTest {

    @Autowired
    CustomerRepo customerRepo;
    boolean isInitialized = false;

    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            customerRepo.save(new Customer("f1", "l1", "p1", "a1", "u1", "p1", "e1"));
            customerRepo.save(new Customer("f2", "l2", "p2", "a2", "u2", "p2", "e2"));
            isInitialized = true;
        }
    }
}
