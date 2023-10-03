package com.example.adventurexp.repository;

import com.example.adventurexp.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepo extends JpaRepository<Shift,Integer> {
}
