package com.example.adventurexp.adventure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.adventurexp.security.entity.UserWithRoles;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public class Employee extends UserWithRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @Column
    private String address;
    /*
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Shift> shifts; */

    public Employee(String firstName, String lastName, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        //this.shifts = new ArrayList<>();
    }
/*
    public void addShift(Shift shift){
        if (shifts == null)
            shifts = new ArrayList<>();
        shifts.add(shift);
    }
    */

}
