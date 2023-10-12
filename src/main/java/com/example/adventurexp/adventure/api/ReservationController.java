package com.example.adventurexp.adventure.api;

import com.example.adventurexp.adventure.dto.ReservationRequest;
import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.service.ReservationService;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping(path = "/{id}")
    ReservationResponse findById(@PathVariable int id) {
        return reservationService.findById(id);
    }

//    @GetMapping(path = "/{username}")
//    List<ReservationResponse> getReservationsByCustomer(@PathVariable String username) {
//        return reservationService.getReservationsByCustomer(username);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ReservationResponse makeReservation(@RequestBody ReservationRequest body, Principal principal) {
        body.setUsername(principal.getName());
        return reservationService.makeReservation(body);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Boolean> editReservation(@RequestBody ReservationRequest body, @PathVariable int id) {
        return reservationService.editReservation(body, id);
    }

    @DeleteMapping(path = "/{id}")
    void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }

}
