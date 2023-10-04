package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrangementRepo extends JpaRepository<Reservation,Integer> {
}
