package com.example.adventurexp.adventure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany()
    private List<Activity> activities;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate reservationEnd;

    public Reservation(Customer customer, LocalDate reservationStart, LocalDate reservationEnd) {
        this.customer = customer;
        this.activities = new ArrayList<>();
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        customer.addReservation(this);
    }

    public void addActivity(Activity activity){
        if (activities == null)
            activities = new ArrayList<>();
        else
            activities.add(activity);
    }

}
