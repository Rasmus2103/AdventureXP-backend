package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    List<ReservationResponse> findByCustomer(Customer customer);
    Optional<Reservation> findById(int id);

    List<Reservation> findAllByActivity(Activity activity);

}
