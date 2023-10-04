package com.example.adventurexp.service;

import com.example.adventurexp.adventure.repository.ShiftRepo;
import com.example.adventurexp.adventure.entity.ShiftService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class shiftServiceTest {


    @Autowired
    ShiftRepo shiftRepo;
    ShiftService shiftService;

    @BeforeEach
    void setUp() {

    }



}
