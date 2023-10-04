package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Arrangement;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrangementResponse {

    int id;
    CustomerResponse customer;
    int participants;
    List<ReservationResponse> reservations;
    String name;
    double aggregatePrice;
    @JsonFormat(pattern = "yyyy-MM-dd-hh", shape = JsonFormat.Shape.STRING)
    LocalDateTime arrangementStart;
    @JsonFormat(pattern = "yyyy-MM-dd-hh", shape = JsonFormat.Shape.STRING)
    LocalDateTime arrangementEnd;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate edited;

    public ArrangementResponse(Arrangement arrangement, boolean includeAll, boolean includeAllCustomer, boolean includeAllReservations) {
        this.customer = new CustomerResponse(arrangement.getCustomer(), includeAllCustomer);
        this.participants = arrangement.getParticipants();
        this.reservations = arrangement.getReservations().stream()
                .map(reservation -> new ReservationResponse(reservation, includeAll, includeAllCustomer, includeAllReservations))
                .collect(Collectors.toList());
        this.name = arrangement.getName();
        this.aggregatePrice = arrangement.getAggregatePrice();
        this.arrangementStart = arrangement.getArrangementStart();
        this.arrangementEnd = arrangement.getArrangementEnd();

        if (includeAll) {
            this.id = arrangement.getId();
            this.created = arrangement.getCreated();
            this.edited = arrangement.getEdited();
        }
    }
}
