package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class customerRepositoryTest {

    @Autowired
    CustomerRepo customerRepo;

    @BeforeEach
    void setUp() {

    }
}
