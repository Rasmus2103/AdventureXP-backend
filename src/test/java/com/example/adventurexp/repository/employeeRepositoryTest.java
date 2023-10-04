package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class employeeRepositoryTest {

    @Autowired
    EmployeeRepo employeeRepo;

    @BeforeEach
    void setUp() {

    }
}
