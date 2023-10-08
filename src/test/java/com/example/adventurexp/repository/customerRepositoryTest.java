package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

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

    @Test
    void findByUsername(){
        Customer customer = customerRepo.findByUsername("u1");
        assertEquals("u1", customer.getUsername());
    }

    @Test
    void findByUsernameFail(){
        Customer customer = customerRepo.findByUsername("u1");
        assertNotSame("u2", customer.getUsername());
    }
}
