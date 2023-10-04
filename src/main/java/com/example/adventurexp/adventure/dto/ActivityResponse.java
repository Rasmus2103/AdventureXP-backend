package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.mapping.List;

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
    private double price;
    private int minAge;
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate edited;

    public ActivityResponse(Activity activity, boolean includeAll) {
        this.name = activity.getName();
        this.price = activity.getPrice();
        this.minAge = activity.getMinAge();
        this.capacity = activity.getCapacity();
        if (includeAll) {
            this.id = activity.getId();
            this.created = activity.getCreated();
            this.edited = activity.getEdited();
        }
    }

}

