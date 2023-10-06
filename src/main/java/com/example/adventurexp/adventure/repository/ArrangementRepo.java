package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Arrangement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrangementRepo extends JpaRepository<Arrangement,Integer> {

    Arrangement findArrangementById(int id);
}
