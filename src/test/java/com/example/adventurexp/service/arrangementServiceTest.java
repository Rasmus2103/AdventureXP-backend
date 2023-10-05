package com.example.adventurexp.service;

import com.example.adventurexp.adventure.entity.Arrangement;
import com.example.adventurexp.adventure.repository.ArrangementRepo;
import com.example.adventurexp.adventure.service.ArrangementService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class arrangementServiceTest {

    @Autowired
    ArrangementRepo arrangementRepo;
    ArrangementService arrangementService;

    Arrangement a1, a2;


    @BeforeEach
    void setUp() {

    }
}
