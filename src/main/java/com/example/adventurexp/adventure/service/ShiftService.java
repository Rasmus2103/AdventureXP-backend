package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {

    ShiftRepo shiftRepo;

    public ShiftService(ShiftRepo shiftRepo) {
        this.shiftRepo = shiftRepo;
    }
}
