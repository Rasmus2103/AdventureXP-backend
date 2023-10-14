package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Arrangement;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.ArrangementRepo;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.repository.ReservationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@DataJpaTest
public class arrangementRepositoryTest {

    @Autowired
    ArrangementRepo arrangementRepo;
    @Autowired
    ReservationRepo reservationRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ActivityRepo activityRepo;
    boolean isInitialized = false;

    Activity activity1, activity2;
    Arrangement arrangement1, arrangement2;
    Reservation r1, r2;
    Customer c1, c2;

    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            c1 = customerRepo.save(new Customer("f1", "l1", "p1", "a1", "u1", "p1", "e1"));
            c2 = customerRepo.save(new Customer("f2", "l2", "p2", "a2", "u2", "p2", "e2"));
            activity1 = activityRepo.save(new Activity("a1", 100, 1, 1));
            activity2 = activityRepo.save(new Activity("a2", 200, 2, 2));
            r1 = reservationRepo.save(new Reservation(c1, 8, activity1, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
            r2 = reservationRepo.save(new Reservation(c2, 3, activity2, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
            arrangement1 = arrangementRepo.save(new Arrangement(c1, 10, "name1", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
            arrangement1.setReservations(List.of(r1));
            arrangement2 = arrangementRepo.save(new Arrangement(c2, 20, "name2", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
            arrangement2.setReservations(List.of(r2));
            isInitialized = true;
        }
    }

    @Test
    void findArrangementById() {
        Arrangement a1 = arrangementRepo.findArrangementById(arrangement1.getId());
        assertEquals(arrangement1.getId(), a1.getId());
    }

    @Test
    void findArrangementByIdFail() {
        Arrangement a1 = arrangementRepo.findArrangementById(1);
        //assertNotSame(2, a1.getId());
    }
}
