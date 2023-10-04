package com.example.adventurexp.adventure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArrangementRequest {

    String name;
    String customerUsername;
    int participants;
    List<ReservationRequest> reservations;

    public ArrangementRequest(ArrangementResponse arrangementResponse) {
        this.name = arrangementResponse.getName();
        this.customerUsername = arrangementResponse.getCustomer().getUsername();
        this.participants = arrangementResponse.getParticipants();
        this.reservations = arrangementResponse.getReservations().stream()
                .map(reservationResponse -> new ReservationRequest(reservationResponse))
                .collect(Collectors.toList());
    }
}
