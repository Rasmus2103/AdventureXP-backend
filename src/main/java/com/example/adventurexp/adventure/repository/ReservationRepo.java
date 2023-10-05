package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    List<Reservation> findByCustomer(Customer customer);

    List<Reservation> findByActivity(Activity activity);

}
