package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    List<ReservationResponse> reservations;

    public ActivityResponse(Activity activity, boolean includeAll) {
        this.name = activity.getName();
        this.pricePrHour = activity.getPrice();
        this.minAge = activity.getMinAge();
        this.capacity = activity.getCapacity();
        if (includeAll) {
            this.id = activity.getId();
            this.created = activity.getCreated();
            this.edited = activity.getEdited();
            this.reservations = activity.getReservations().stream().map(r -> new ReservationResponse(r, true, true, true)).toList();
        }
    }

    public void addReservation(ReservationResponse reservation) {
        reservations.add(reservation);
    }

}

