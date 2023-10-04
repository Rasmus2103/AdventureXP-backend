package com.example.adventurexp.adventure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReservationRequest {

    String username;
    int participants;
    int activityId;
    @JsonFormat(pattern = "yyyy-MM-dd-hh", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd-hh", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationEnd;

    public ReservationRequest(ReservationResponse r){
        this.username = r.getCustomer().getUsername();
        this.participants = r.getParticipants();
        this.activityId = r.getActivity().getId();
        this.reservationStart = r.getReservationStart();
        this.reservationEnd = r.getReservationEnd();
    }

}
