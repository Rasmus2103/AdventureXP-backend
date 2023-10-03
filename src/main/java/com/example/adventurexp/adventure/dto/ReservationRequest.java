package com.example.adventurexp.adventure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReservationRequest {

    String username;
    List<Integer> activityIds;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationEnd;

    public ReservationRequest(ReservationResponse r){
        this.username = r.getCustomer().getUsername();
        this.activityIds = r.getActivitiesAsId();
        this.reservationStart = r.getReservationStart();
        this.reservationEnd = r.getReservationEnd();
    }

}
