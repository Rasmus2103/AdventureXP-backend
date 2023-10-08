package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ArrangementRequest;
import com.example.adventurexp.adventure.dto.ArrangementResponse;
import com.example.adventurexp.adventure.service.ArrangementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/arrangements")
public class ArrangementController {

    private final ArrangementService arrangementService;

    public ArrangementController(ArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }

    @GetMapping("/{id}")
    public ArrangementResponse getArrangementById(@PathVariable int id) {
        return arrangementService.findById(id);
    }

    @GetMapping()
    public List<ArrangementResponse> getAllArrangements() {
        return arrangementService.getAllArrangements(true, false, false);
    }

    @PostMapping()
    public ArrangementResponse createArrangement(@RequestBody ArrangementRequest body) {
        return arrangementService.createArrangement(body);
    }

    @PutMapping("/{id}")
    public ArrangementResponse editArrangement(@PathVariable int id, @RequestBody ArrangementRequest body) {
        return arrangementService.editArrangement(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteArrangement(@PathVariable int id) {
        arrangementService.deleteArrangement(id);
    }
}
