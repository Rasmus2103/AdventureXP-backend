package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.*;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.entity.Shift;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import com.example.adventurexp.adventure.service.EmployeeService;
import com.example.adventurexp.adventure.service.ShiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class shiftServiceTest {


    @Autowired
    ShiftRepo shiftRepo;
    ShiftService shiftService;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    ActivityRepo activityRepo;

    Shift s1, s2;
    Employee e1, e2;
    Activity a1, a2;

    @BeforeEach
    void setUp() {
        e1 = employeeRepo.save(new Employee("f1", "l1", "p1", "a1", "u1", "p1", "e1" ));
        e2 = employeeRepo.save(new Employee("f2", "l2", "p2", "a2", "u2", "p2", "e2" ));
        a1 = activityRepo.save(new Activity("a1", 100, 1,1));
        a2 = activityRepo.save(new Activity("a2", 200, 2,2));
        s1 = shiftRepo.save(new Shift(e1, a1, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
        s2 = shiftRepo.save(new Shift(e2, a2, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
        shiftService = new ShiftService(shiftRepo, employeeRepo, activityRepo);

    }

    @Test
    void getShifts() {
        List<ShiftResponse> shiftResponse = shiftService.getShifts(true);
        EmployeeResponse employeeResponse = shiftResponse.get(0).getEmployeeResponse();
        LocalDateTime startDate = shiftResponse.get(0).getShiftStart();
        LocalDateTime endDate = shiftResponse.get(0).getShiftEnd();
        assertNotNull(employeeResponse);
        assertNotNull(startDate);
        assertNotNull(endDate);
    }

    /*
    @Test
    void testFindByIdFound(){
        ReservationResponse response = shiftService.findById(s1.getId());
        assertEquals(s1.getId(), response.getId());
    }

    @Test
    void findByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> shiftService.findById(3));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
    */

    @Test
    void addShiftSucces() {
        ShiftRequest s3 = new ShiftRequest(e1.getUsername(), a1.getId(), LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(5));
        ShiftResponse response = shiftService.addShift(s3, true);
        assertEquals(e1.getUsername(), response.getEmployeeResponse().getUsername());

    }


    @Test
    void addShiftStartDateBeforeToday() {
        ShiftRequest s3 = new ShiftRequest(e1.getUsername(),a1.getId(), LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(5));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> shiftService.addShift(s3, true));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }



}
