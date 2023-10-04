package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    int participants;
    ActivityResponse activity;
    double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd-hh", shape = JsonFormat.Shape.STRING)
    LocalDateTime reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd-hh", shape = JsonFormat.Shape.STRING)
    LocalDateTime reservationEnd;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate edited;

    public ReservationResponse(Reservation reservation, boolean includeAll, boolean includeAllCustomer, boolean includeAllActivities) {
        this.customer = new CustomerResponse(reservation.getCustomer(), includeAllCustomer);
        this.participants = reservation.getParticipants();
        this.activity = new ActivityResponse(reservation.getActivity(), includeAllActivities);
        this.totalPrice = reservation.getTotalPrice();
        this.reservationStart = reservation.getReservationStart();
        this.reservationEnd = reservation.getReservationEnd();
        if (includeAll) {
            this.id = reservation.getId();
            this.created = reservation.getCreated();
            this.edited = reservation.getEdited();
        }
    }

}
