package com.example.adventurexp.service;

import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class activityServiceTest {

    @Autowired
    ActivityRepo activityRepo;
    ActivityService activityService;

    @BeforeEach
    void setUp() {

    }
}
