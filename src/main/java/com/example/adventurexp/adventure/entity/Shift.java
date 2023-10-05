package com.example.adventurexp.adventure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Activity activity;
    @ManyToOne()
    private Employee employee;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime shiftStart;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime shiftEnd;

    public Shift(Employee employee, Activity activity ,LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        this.employee = employee;
        this.activity = activity;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        employee.addShift(this);
    }

}
