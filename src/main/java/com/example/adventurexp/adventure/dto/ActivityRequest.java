package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Activity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ActivityRequest {

    private String name;
    private double price;
    private int minAge;
    private int capacity;

    public static Activity getActivityEntity(ActivityRequest activity){
        return new Activity(activity.getName(), activity.getPrice(),
                activity.getMinAge(), activity.getCapacity());
    }

    public ActivityRequest(Activity activity){
        this.name = activity.getName();
        this.price = activity.getPricePrHour();
        this.minAge = activity.getMinAge();
        this.capacity = activity.getCapacity();
    }

}
