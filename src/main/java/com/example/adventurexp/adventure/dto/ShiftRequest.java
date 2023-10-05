package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShiftRequest {

    private String employeeUsername;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime shiftStart;
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime shiftEnd;

    public ShiftRequest(Shift shift) {
        this.employeeUsername = shift.getEmployee().getUsername();
        this.shiftStart = shift.getShiftStart();
        this.shiftEnd = shift.getShiftEnd();
    }

}
