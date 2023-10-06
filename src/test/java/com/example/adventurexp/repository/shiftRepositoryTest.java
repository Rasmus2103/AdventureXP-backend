package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.entity.Shift;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class shiftRepositoryTest {

    @Autowired
    ShiftRepo shiftRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    ActivityRepo activityRepo;

    boolean isInitialized = false;

    Shift s1, s2;
    Employee e1, e2;
    Activity a1, a2;

    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            e1 = employeeRepo.save(new Employee("f1", "l1", "p1", "a1", "u1", "p1", "e1"));
            e2 = employeeRepo.save(new Employee("f2", "l2", "p2", "a2", "u2", "p2", "e2"));
            a1 = activityRepo.save(new Activity("a1", 100, 1, 1));
            a2 = activityRepo.save(new Activity("a2", 200, 2, 2));
            s1 = shiftRepo.save(new Shift(e1, a1, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
            s2 = shiftRepo.save(new Shift(e2, a2, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
            isInitialized = true;
        }
    }
}
