package com.example.adventurexp.adventure.repository;

import com.example.adventurexp.adventure.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity,Integer> {
    Activity findAllById(int id);
}
