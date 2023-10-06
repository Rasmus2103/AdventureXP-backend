package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ActivityRequest;
import com.example.adventurexp.adventure.dto.ActivityResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public ActivityResponse findById(int id) {
        Activity activity = getActivityById(id);
        return new ActivityResponse(activity, true);
    }

    public ResponseEntity<Boolean> editActivity(ActivityRequest body, int id) {
        Activity activity = getActivityById(id);
        activity.setName(body.getName());
        activity.setCapacity(body.getCapacity());
        activity.setMinAge(body.getMinAge());
        activity.setPrice(body.getPricePrHour());
        activityRepo.save(activity);
        return ResponseEntity.ok(true);
    }
    public ActivityResponse addActivity(ActivityRequest body) {
      Activity activity = ActivityRequest.getActivityEntity(body);
      activity = activityRepo.save(activity);
        return new ActivityResponse(activity, true);
    }


    public ResponseEntity<Boolean> deleteActivity(int id) {
        Activity activity = getActivityById(id);
        activityRepo.delete(activity);
        return ResponseEntity.ok(true);
    }
    private Activity getActivityById(int id) {
        return activityRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No activity with this ID is found"));
    }

}
