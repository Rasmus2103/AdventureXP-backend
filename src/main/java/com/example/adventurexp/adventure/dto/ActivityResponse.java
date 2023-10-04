package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResponse {

    int id;
    private String name;
    private double pricePrHour;
    private int minAge;
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate edited;

    public ActivityResponse(Activity activity, boolean includeAll) {
        this.name = activity.getName();
        this.pricePrHour = activity.getPrice();
        this.minAge = activity.getMinAge();
        this.capacity = activity.getCapacity();
        if (includeAll) {
            this.id = activity.getId();
            this.created = activity.getCreated();
            this.edited = activity.getEdited();
        }
    }

}
