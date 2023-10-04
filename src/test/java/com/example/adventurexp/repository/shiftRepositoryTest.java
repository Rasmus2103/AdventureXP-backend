package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class shiftRepositoryTest {

    @Autowired
    ShiftRepo shiftRepo;


    @BeforeEach
    void setUp() {

    }
}
