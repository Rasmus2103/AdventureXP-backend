package com.example.adventurexp.repository;

import com.example.adventurexp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity,Integer> {
}
