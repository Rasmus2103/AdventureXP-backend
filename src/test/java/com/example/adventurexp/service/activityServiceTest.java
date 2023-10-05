package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class activityServiceTest {

    @Autowired
    ActivityRepo activityRepo;
    ActivityService activityService;

    Activity a1, a2;
    @BeforeEach
    void setUp() {
        a1 = activityRepo.save(new Activity("a1", 100, 1,1));
        a2 = activityRepo.save(new Activity("a2", 200, 2,2));
        activityService = new ActivityService(activityRepo); //Set up activityService with the mock (H2) database
    }

    @Test
    void testGetActivitiesAllDetails() {
        List<ActivityResponse> activityResponses = activityService.getActivities(true);
        LocalDate time = activityResponses.get(0).getCreated();
        assertNotNull(time, "expects date to be set when true is passed for getactivities");
    }

    @Test
    void testGetActivitiesNoDetails() {
        List<ActivityResponse> activityResponses = activityService.getActivities(false);
        LocalDate time = activityResponses.get(0).getCreated();
        assertNotNull(time, "expects date to not be set when false is passed for getactivities");
    }

    /*
    @Test
    void testFindByIdFound() {
        ActivityResponse response = activityService.findById(a1.getId());
        assertEquals("a1", response.getName());
    }

     @Test
    void testFindByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> activityService.findById("i dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    */



}
