package com.example.adventurexp.adventure.dto;

import com.example.adventurexp.adventure.entity.Customer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public static Customer getCustomerEntity(CustomerRequest customer){
        return new Customer(customer.getFirstName(), customer.getLastName(),
                customer.getPhoneNumber(), customer.getAddress(), customer.getUsername(),
                customer.getPassword(), customer.getEmail());
    }

    public CustomerRequest(Customer customer){
        this.username = customer.getUsername();
        this.password = customer.getPassword();
        this.email = customer.getEmail();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
    }
}
