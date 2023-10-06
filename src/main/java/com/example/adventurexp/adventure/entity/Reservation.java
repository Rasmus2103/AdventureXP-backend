package com.example.adventurexp.adventure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Reservation extends AdminDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    private Customer customer;
    @Column
    private int participants;
    @ManyToOne()
    private Activity activity;
    @Column
    private double totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationEnd;

    public Reservation(Customer customer, int participants, Activity activity, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.customer = customer;
        this.participants = participants;
        this.activity = activity;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        calculateTotalPrice();
        customer.addReservation(this);
        activity.addReservation(this);
    }

    public void calculateTotalPrice() {
        double pricePerHour = activity.getPrice();
        double durationInHours = Duration.between(reservationStart, reservationEnd).toHours();
        this.totalPrice = (int) (pricePerHour * durationInHours);
    }

}
