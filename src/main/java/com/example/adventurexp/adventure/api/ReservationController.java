package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ReservationRequest;
import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.service.ReservationService;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/reservation")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    List<ReservationResponse> getReservations(boolean includeAllCustomer, boolean includeAllActivities) {
        return reservationService.getReservations(true, includeAllCustomer, includeAllActivities);
    }

    @GetMapping(path = "/{username}")
    List<Reservation> getReservationsByCustomer(@PathVariable String username) {
        return reservationService.getReservationsByCustomer(username);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationResponse makeReservation(@RequestBody ReservationRequest body) {
        return reservationService.makeReservation(body);
    }

    @DeleteMapping(path = "/{id}")
    void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

}
