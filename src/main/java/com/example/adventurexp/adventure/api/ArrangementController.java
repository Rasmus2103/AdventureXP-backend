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

    /**
     * GET /arrangements/{id}
     */
    @GetMapping("/{id}")
    public ArrangementResponse getArrangementById(@PathVariable int id) {
        return arrangementService.getArrangementById(id);
    }

    @GetMapping("/all")
    public List<ArrangementResponse> getAllArrangements() {
        return arrangementService.getAllArrangements(true, false, false);
    }

    @GetMapping("name/{arrangementName}")
    public ArrangementResponse getArrangementByName(@PathVariable String arrangementName) {
        return arrangementService.getArrangementByName(arrangementName);
    }

    @PostMapping("/create")
    public ArrangementResponse createArrangement(@RequestBody ArrangementRequest body) {
        return arrangementService.createArrangement(body);
    }

    @PutMapping("/edit/{id}")
    public ArrangementResponse editArrangement(@PathVariable int id, @RequestBody ArrangementRequest body,
                                               @RequestBody List<Integer> reservationIds) {
        return arrangementService.editArrangement(id, body, reservationIds);
    }

    /**
     * DELETE /arrangements/{id}
     */
    @DeleteMapping("/delete/{id}")
    public void deleteArrangement(@PathVariable int id) {
        arrangementService.deleteArrangement(id);
    }
}
