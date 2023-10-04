package com.example.adventurexp.adventure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Shift extends AdminDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    private Employee employee;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate shiftStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate shiftEnd;

    public Shift(Employee employee, LocalDate shiftStart, LocalDate shiftEnd) {
        this.employee = employee;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        employee.addShift(this);
    }

}
