package com.example.adventurexp.adventure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    List<Integer> reservationIds;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime arrangementStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime arrangementEnd;

    public ArrangementRequest(ArrangementResponse arrangementResponse) {
        this.name = arrangementResponse.getName();
        this.customerUsername = arrangementResponse.getCustomer().getUsername();
        this.participants = arrangementResponse.getParticipants();
        this.reservationIds = arrangementResponse.getReservations().stream().map(ReservationResponse::getId).collect(Collectors.toList());
    }
}
