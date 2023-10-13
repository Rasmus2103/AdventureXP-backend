package com.example.adventurexp.adventure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Arrangement extends AdminDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    private Customer customer;
    @Column
    private int participants;
    @ManyToMany
    private List<Reservation> reservations;
    @Column
    private String name;
    @Column
    private double aggregatePrice;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime arrangementStart;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime arrangementEnd;

    public Arrangement(Customer customer, int participants, String name, LocalDateTime arrangementStart, LocalDateTime arrangementEnd) {
        this.customer = customer;
        this.participants = participants;
        this.reservations = new ArrayList<>();
        this.name = name;
        this.arrangementStart = arrangementStart;
        this.arrangementEnd = arrangementEnd;
        this.aggregatePrice = calculateAggregatePrice();
    }

    public void addReservation(Reservation reservation){
        if (reservations == null)
            reservations = new ArrayList<>();
        else
            reservations.add(reservation);
    }

    private double calculateAggregatePrice() {
        double price = 0;
        for (Reservation reservation : reservations) {
            price += reservation.getTotalPrice();
        }
        return price;
    }
}
