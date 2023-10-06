package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ShiftRequest;
import com.example.adventurexp.adventure.dto.ShiftResponse;
import com.example.adventurexp.adventure.service.ShiftService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return shiftService.getShifts(true);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ShiftResponse addShift(@RequestBody ShiftRequest body, boolean includeAll) {
        return shiftService.addShift(body, includeAll);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Boolean> editShift(@RequestBody ShiftRequest body, @PathVariable int id) {
        return shiftService.editShift(body, id);
    }

    @DeleteMapping(path = "/{id}")
    void deleteShift(@PathVariable int id) {
        shiftService.deleteShift(id);
    }

}
