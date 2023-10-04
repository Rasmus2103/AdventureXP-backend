package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ShiftResponse;
import com.example.adventurexp.adventure.service.ShiftService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/shift")
public class ShiftController {
    ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping
    List<ShiftResponse> getShifts() {
        return shiftService.getShifts(false);
    }
}
