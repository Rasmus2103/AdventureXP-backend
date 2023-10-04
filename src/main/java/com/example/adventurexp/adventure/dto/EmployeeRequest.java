package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Employee;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeRequest {


    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public static Employee getEmployeeEntity(EmployeeRequest employee){
        return new Employee(employee.getFirstName(), employee.getLastName(),
                employee.getPhoneNumber(), employee.getAddress(), employee.getUsername(),
                employee.getPassword(), employee.getEmail());
    }

    public EmployeeRequest(Employee employee){
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.email = employee.getEmail();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.phoneNumber = employee.getPhoneNumber();
        this.address = employee.getAddress();
    }
}
