package com.example.adventurexp.adventure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.adventurexp.security.entity.UserWithRoles;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public class Employee extends UserWithRoles {

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String address;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Shift> shifts;

    public Employee(String firstName, String lastName, String phoneNumber, String address, String username, String password, String email) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.shifts = new ArrayList<>();
    }

    public void addShift(Shift shift){
        if (shifts == null)
            shifts = new ArrayList<>();
        shifts.add(shift);
    }


}
