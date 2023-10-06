package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.repository.ReservationRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class reservationRepositoryTest {

    @Autowired
    ReservationRepo reservationRepo;

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ActivityRepo activityRepo;
    @Autowired
    ShiftRepo shiftRepo;

    boolean isInitialized = false;

    Reservation r1, r2;
    Activity a1, a2;
    Customer c1, c2;

    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            a1 = activityRepo.save(new Activity("a1", 100, 1,1));
            a1.setId(1);
            a2 = activityRepo.save(new Activity("a2", 200, 2,2));
            c1 = customerRepo.save(new Customer("f1", "l1", "p1", "a1", "u1", "p1", "e1" ));
            c2 = customerRepo.save(new Customer("f2", "l2", "p2", "a2", "u2", "p2", "e2" ));
            r1 = reservationRepo.save(new Reservation(c1, 8,  a1, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
            r2 = reservationRepo.save(new Reservation(c2, 3, a2, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
            isInitialized = true;
        }
    }
}
