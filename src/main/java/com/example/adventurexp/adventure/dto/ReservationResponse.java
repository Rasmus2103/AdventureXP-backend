package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponse {

    int id;
    CustomerResponse customer;
    List<ActivityResponse> activities;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate reservationEnd;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate edited;

    public ReservationResponse(Reservation reservation, boolean includeAll, boolean includeAllCustomer, boolean includeAllActivities) {
        this.customer = new CustomerResponse(reservation.getCustomer(), includeAllCustomer);
        this.activities = new ArrayList<>();
        reservation.getActivities().stream().map(a -> new ActivityResponse(a, includeAllActivities)).forEach(a -> activities.add(a));
        this.reservationStart = reservation.getReservationStart();
        this.reservationEnd = reservation.getReservationEnd();
        if (includeAll) {
            this.id = reservation.getId();
            this.created = reservation.getCreated();
            this.edited = reservation.getEdited();
        }
    }

    public List<Integer> getActivitiesAsId() {
        List<Integer> activityIds = new ArrayList<>();
        activities.stream().map(a -> a.getId()).forEach(a -> activityIds.add(a));
        return activityIds;
    }

}
