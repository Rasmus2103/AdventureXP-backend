package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShiftResponse {

    private int id;
    private EmployeeResponse employeeResponse;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate shiftStart;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate shiftEnd;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate edited;

    public ShiftResponse(Shift shift, boolean includeAll) {
        this.employeeResponse = new EmployeeResponse(shift.getEmployee(), false);
        this.shiftStart = shift.getShiftStart();
        this.shiftEnd = shift.getShiftEnd();
        if (includeAll) {
            this.id = shift.getId();
            this.created = shift.getCreated();
            this.edited = shift.getEdited();
        }
    }
}
