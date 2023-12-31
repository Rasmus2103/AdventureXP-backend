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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    LocalDateTime reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    LocalDateTime reservationEnd;

    public ReservationRequest(ReservationResponse r){
        this.username = r.getCustomer().getUsername();
        this.participants = r.getParticipants();
        this.activityId = r.getActivity().getId();
        this.reservationStart = r.getReservationStart();
        this.reservationEnd = r.getReservationEnd();
    }

}
