package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.dto.CustomerResponse;
import com.example.adventurexp.adventure.dto.ReservationRequest;
import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.*;
import com.example.adventurexp.adventure.repository.*;
import com.example.adventurexp.adventure.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class reservationServiceTest {

    @Autowired
    ReservationRepo reservationRepo;
    ReservationService reservationService;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ActivityRepo activityRepo;
    @Autowired
    ShiftRepo shiftRepo;
    @Autowired
    EmployeeRepo employeeRepo;

    Reservation r1, r2;
    Activity a1, a2;
    Customer c1, c2;
    Shift s1, s2;
    Employee e1, e2;

    @BeforeEach
    void setUp() {
        a1 = activityRepo.save(new Activity("a1", 100, 1,1));
        a2 = activityRepo.save(new Activity("a2", 200, 2,2));
        e1 = employeeRepo.save(new Employee("f1", "l1", "p1", "a1", "u1", "p1", "e1"));
        e2 = employeeRepo.save(new Employee("f2", "l2", "p2", "a2", "u2", "p2", "e2"));
        s1 = shiftRepo.save(new Shift(e1, a1, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
        s2 = shiftRepo.save(new Shift(e2, a2, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
        c1 = customerRepo.save(new Customer("f1", "l1", "p1", "a1", "u11", "p1", "e11" ));
        c2 = customerRepo.save(new Customer("f2", "l2", "p2", "a2", "u22", "p2", "e22" ));
        r1 = reservationRepo.save(new Reservation(c1, 8,  a1, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        r2 = reservationRepo.save(new Reservation(c2, 3, a2, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        reservationService = new ReservationService(reservationRepo, customerRepo, activityRepo, shiftRepo);
    }


    @Test
    void testGetReservations() {
        List<ReservationResponse> reservationResponses = reservationService.getReservations(true, true, true);
        ActivityResponse activityResponse = reservationResponses.get(0).getActivity();
        CustomerResponse customerResponse = reservationResponses.get(0).getCustomer();
        LocalDateTime timeStart = reservationResponses.get(0).getReservationStart();
        LocalDateTime timeEnd = reservationResponses.get(0).getReservationEnd();
        assertNotNull(timeStart);
        assertNotNull(timeEnd);
        assertNotNull(activityResponse);
        assertNotNull(customerResponse);
    }

    @Test
    void findById() {
        ReservationResponse reservationResponse = reservationService.findById(r1.getId());
        assertEquals(r1.getId(), reservationResponse.getId());
    }

    @Test
    void findByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reservationService.findById(3));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void addReservationFailActivityIsReservedInThisPeriod() {
        ReservationRequest r3 = new ReservationRequest(c1.getUsername(), 8, a1.getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reservationService.makeReservation(r3));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void addReservationNoEmployeeAvailable() {
        ReservationRequest r3 = new ReservationRequest(c1.getUsername(), 8, a1.getId(), LocalDateTime.now().plusDays(2000), LocalDateTime.now().plusDays(3000));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reservationService.makeReservation(r3));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void addReservationSucces(){
        ReservationRequest r3 = new ReservationRequest(c1.getUsername(), 8, a2.getId(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        ReservationResponse response = reservationService.makeReservation(r3);
        assertEquals(c1.getUsername(), response.getCustomer().getUsername());
        assertEquals(a2.getName(), response.getActivity().getName());
    }


    @Test
    void testEditReservation() {
        assertEquals(ResponseEntity.ok(true), reservationService.editReservation(new ReservationRequest(c1.getUsername(), 100, a1.getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(10)), r1.getId()));
    }

    @Test
    void testDeleteReservationSucces() {
        reservationService.deleteReservation(r1.getId());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                reservationService.findById(r1.getId()));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}
