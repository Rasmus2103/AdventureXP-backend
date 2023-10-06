package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.service.ActivityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
