package com.example.adventurexp.service;

import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.entity.Shift;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import com.example.adventurexp.adventure.service.EmployeeService;
import com.example.adventurexp.adventure.service.ShiftService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class shiftServiceTest {


    @Autowired
    ShiftRepo shiftRepo;
    ShiftService shiftService;

    @Autowired
    EmployeeRepo employeeRepo;

    Shift s1, s2;
    Employee e1, e2;

    @BeforeEach
    void setUp() {
        e1 = employeeRepo.save(new Employee("f1", "l1", "p1", "a1", "u1", "p1", "e1" ));
        e2 = employeeRepo.save(new Employee("f2", "l2", "p2", "a2", "u2", "p2", "e2" ));
        shiftService = new ShiftService(shiftRepo, employeeRepo);

    }



}
