package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepo extends JpaRepository<Shift,Integer> {
}
