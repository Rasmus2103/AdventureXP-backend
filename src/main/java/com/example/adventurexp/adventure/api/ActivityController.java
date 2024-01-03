package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ActivityRequest;
import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.service.ActivityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/activity")
public class ActivityController {

    ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    List<ActivityResponse> getActivities() {
        return activityService.getActivities(true);
    }

    @GetMapping(path = "/{id}")
    ActivityResponse findById(@PathVariable int id) {
        return activityService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ActivityResponse addActivity(@RequestBody ActivityRequest body) {
        return activityService.addActivity(body);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Boolean> editActivity(@RequestBody ActivityRequest body, @PathVariable int id) {
        return activityService.editActivity(body, id);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Boolean> deleteAcitivity(@PathVariable int id) {
        return activityService.deleteActivity(id);
    }

}
