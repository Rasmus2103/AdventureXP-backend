package com.example.adventurexp.service;

import com.example.adventurexp.adventure.repository.ReservationRepo;
import com.example.adventurexp.adventure.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class reservationServiceTest {

    @Autowired
    ReservationRepo reservationRepo;
    ReservationService reservationService;


    @BeforeEach
    void setUp() {

    }




}
