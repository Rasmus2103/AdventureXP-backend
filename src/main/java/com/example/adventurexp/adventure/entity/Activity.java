package com.example.adventurexp.adventure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Activity extends AdminDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private int minAge;
    @Column
    private int capacity;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public Activity(String name, double price, int minAge, int capacity) {
        this.name = name;
        this.price = price;
        this.minAge = minAge;
        this.capacity = capacity;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation){
        if (reservations == null)
            reservations = new ArrayList<>();
        reservations.add(reservation);
    }

}
