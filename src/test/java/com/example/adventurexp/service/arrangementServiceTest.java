package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.ArrangementRequest;
import com.example.adventurexp.adventure.dto.ArrangementResponse;
import com.example.adventurexp.adventure.dto.CustomerResponse;
import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Arrangement;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.ArrangementRepo;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.repository.ReservationRepo;
import com.example.adventurexp.adventure.service.ArrangementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class arrangementServiceTest {

    @Autowired
    ArrangementRepo arrangementRepo;
    ArrangementService arrangementService;

    @Autowired
    ReservationRepo reservationRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ActivityRepo activityRepo;

    Activity activity1, activity2;
    Arrangement arrangement1, arrangement2;
    Reservation r1, r2;
    Customer c1, c2;


    @BeforeEach
    void setUp() {
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

        arrangementService = new ArrangementService(arrangementRepo, reservationRepo, customerRepo, activityRepo); //Set up arrangementService with the mock (H2) database
    }


    @Test
    void testGetArrangements() {
        List<ArrangementResponse> arrangementResponses = arrangementService.getAllArrangements(true, true, true);
        ReservationResponse reservationResponse = arrangementResponses.get(0).getReservations().get(0);
        CustomerResponse customerResponse = arrangementResponses.get(0).getCustomer();
        LocalDateTime timeStart = arrangementResponses.get(0).getArrangementStart();
        LocalDateTime timeEnd = arrangementResponses.get(0).getArrangementEnd();
        String Name = arrangementResponses.get(0).getName();
        assertNotNull(reservationResponse);
        assertNotNull(customerResponse);
        assertNotNull(timeStart);
        assertNotNull(timeEnd);
        assertNotNull(Name);
    }

    @Test
    void findById() {
        ArrangementResponse arrangementResponse = arrangementService.findById(arrangement1.getId());
        assertEquals(arrangement1.getId(), arrangementResponse.getId());
    }

    @Test
    void findByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> arrangementService.findById(3));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

/* TODO make these test work and

    @Test
    void testEditArrangement(){
        ArrangementResponse arrangementResponse = new ArrangementResponse(arrangement1, true, true, true);
        assertNotNull(arrangementService.editArrangement(arrangement1.getId(), new ArrangementRequest(arrangementResponse), List.of(r1.getId())));
    }

    @Test
    void testAddArrangement(){
        ArrangementResponse arrangementResponse = new ArrangementResponse(arrangement1, true, true, true);
        assertNotNull(arrangementService.addArrangement(new ArrangementRequest(arrangementResponse), List.of(r1.getId())));
    }

 */

    @Test
    void testDeleteArrangement() {
        arrangementService.deleteArrangement(arrangement1.getId());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> arrangementService.findById(arrangement1.getId()));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
