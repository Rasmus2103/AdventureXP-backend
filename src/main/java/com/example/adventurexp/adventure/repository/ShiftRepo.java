package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepo extends JpaRepository<Shift,Integer> {
    List<Shift> findAllByActivity(Activity activity);

}
