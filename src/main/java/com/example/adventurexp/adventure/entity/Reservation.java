package com.example.adventurexp.adventure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    /*@ManyToMany()
    private List<Activity> activity;*/

}
