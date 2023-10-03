package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.repository.ActivityRepo;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    ActivityRepo activityRepo;
    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }
}
