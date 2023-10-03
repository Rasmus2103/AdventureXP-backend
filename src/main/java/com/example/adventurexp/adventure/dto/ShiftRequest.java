package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShiftRequest {

    private String employeeUsername;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate shiftStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate shiftEnd;

    public ShiftRequest(Shift shift) {
        this.employeeUsername = shift.getEmployee().getUsername();
        this.shiftStart = shift.getShiftStart();
        this.shiftEnd = shift.getShiftEnd();
    }

}
