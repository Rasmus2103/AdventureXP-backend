package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    ActivityRepo activityRepo;

    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    public List<ActivityResponse> getActivities(boolean includeAll) {
        List<Activity> activities = activityRepo.findAll();

        return activities.stream()
                .map(activity -> new ActivityResponse(activity, includeAll))
                .collect(Collectors.toList());
    }

}
