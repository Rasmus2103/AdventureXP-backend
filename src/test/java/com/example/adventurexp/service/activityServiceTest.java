package com.example.adventurexp.service;

import com.example.adventurexp.adventure.dto.ActivityRequest;
import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNull(time, "expects date to not be set when false is passed for getactivities");
    }


    @Test
    void testFindByIdFound() {
        ActivityResponse response = activityService.findById(a1.getId());
        assertEquals("a1", response.getName());
    }

     @Test
    void testFindByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> activityService.findById(3));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testAddActivity() {
        ActivityResponse response = activityService.addActivity(new ActivityRequest("a3", 300, 3,3));
        assertEquals("a3", response.getName());
        assertEquals(300, response.getPricePrHour());
        assertEquals(3, response.getCapacity());
    }

    @Test
    void testUpdateActivitySucces() {
        assertEquals(ResponseEntity.ok(true), activityService.editActivity(new ActivityRequest("a3", 300, 3,3), a1.getId()));
    }

    @Test
    void testUpdateActivityFail() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> activityService.editActivity(new ActivityRequest("a3", 300, 3,3), 3));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testDeleteActivity() {
        assertEquals(ResponseEntity.ok(true), activityService.deleteActivity(a1.getId()));
    }
    
//Skal blive på 100, don't ask questions otherswise you will be the dværg in dværgekast
    @Test
    void testDeleteActivityFail() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> activityService.deleteActivity(100));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

}