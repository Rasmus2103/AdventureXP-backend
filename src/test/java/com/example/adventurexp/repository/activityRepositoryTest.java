package com.example.adventurexp.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class activityRepositoryTest {

    @Autowired
    ActivityRepo activityRepo;
    boolean isInitialized = false;
    @BeforeEach
    void setUp() {
        if (!isInitialized){
            activityRepo.save(new Activity("a1", 100, 1,1));
            activityRepo.save(new Activity("a2", 200, 2,2));
            isInitialized = true;
        }
    }

    @Test
    void findAllById() {
        Activity a1 = activityRepo.findAllById(1);
        //assertEquals("a1", a1.getName());
    }

    @Test
    void findAllByIdFail(){
        Activity a1 = activityRepo.findAllById(1);
        assertNotSame("a2", a1.getName());
    }
}
