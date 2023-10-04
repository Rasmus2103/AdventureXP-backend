package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,String> {
}
