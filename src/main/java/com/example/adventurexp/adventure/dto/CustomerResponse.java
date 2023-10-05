package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Customer;
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
public class CustomerResponse {


    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate edited;
    private double credit;
    List<ReservationResponse> reservations;

    public CustomerResponse(Customer customer, boolean includeAll){
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
        this.credit = customer.getCredit();
        if(includeAll){
            this.created = customer.getCreated();
            this.edited = customer.getEdited();
            this.reservations = customer.getReservations().stream().map(c -> new ReservationResponse(c, true, true, true)).toList();
        }
    }

    public void addReservation(ReservationResponse reservation) {
        reservations.add(reservation);
    }
}
