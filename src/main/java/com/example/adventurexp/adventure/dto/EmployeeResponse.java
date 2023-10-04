package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate created;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate edited;
    List<ShiftResponse> shifts;

    public EmployeeResponse(Employee employee, boolean includeAll){
        this.username = employee.getUsername();
        this.email = employee.getEmail();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.phoneNumber = employee.getPhoneNumber();
        this.address = employee.getAddress();
        if(includeAll){
            this.created = employee.getCreated();
            this.edited = employee.getEdited();
            this.shifts = employee.getShifts().stream().map(c -> new ShiftResponse(c, true)).toList();
        }
    }

    public void addShift(ShiftResponse shift) {
        shifts.add(shift);
    }
}
