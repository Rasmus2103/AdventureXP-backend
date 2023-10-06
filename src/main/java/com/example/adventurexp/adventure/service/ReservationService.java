package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ReservationRequest;
import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.repository.ReservationRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    ReservationRepo reservationRepo;
    CustomerRepo customerRepo;
    ActivityRepo activityRepo;

    public ReservationService(ReservationRepo reservationRepo, CustomerRepo customerRepo, ActivityRepo activityRepo, ShiftRepo shiftRepo) {
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
        this.activityRepo = activityRepo;
    }

    public List<ReservationResponse> getReservations(boolean includeAll, boolean includeAllCustomer, boolean includeAllActivities) {
        List<Reservation> reservations = reservationRepo.findAll();

        List<ReservationResponse> response = reservations.stream().map(reservation -> new ReservationResponse(reservation,
                includeAll,
                includeAllCustomer,
                includeAllActivities)).toList();

        return response;
    }

    public ReservationResponse makeReservation(ReservationRequest body) {
        if(body.getReservationStart().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in past not allowed");
        }

        Customer customer = customerRepo.findByUsername(body.getUsername());
        if(customer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer");
        }

        Activity activity = activityRepo.findAllById(body.getActivityId());
        if(activity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid activity");
        }

        double totalPrice = calculateTotalPrice(activity, body.getReservationStart(), body.getReservationEnd());

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setParticipants(body.getParticipants());
        reservation.setActivity(activity);
        reservation.setReservationStart(body.getReservationStart());
        reservation.setReservationEnd(body.getReservationEnd());
        reservation.setTotalPrice(totalPrice);
        reservation.setCreated(LocalDate.now());
        reservation.setEdited(LocalDate.now());

        Reservation savedReservation = reservationRepo.save(reservation);

        return new ReservationResponse(savedReservation, true, true, true);
    }


    public ResponseEntity<Boolean> editReservation(ReservationRequest body, int id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No reservation with this ID is found"));

        Customer customer = customerRepo.findByUsername(body.getUsername());
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid customer username");
        }

        Activity activity = activityRepo.findById(body.getActivityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid activity ID"));

        double totalPrice = calculateTotalPrice(activity, body.getReservationStart(), body.getReservationEnd());

        reservation.setCustomer(customer);
        reservation.setParticipants(body.getParticipants());
        reservation.setActivity(activity);
        reservation.setTotalPrice(totalPrice);
        reservation.setReservationStart(body.getReservationStart());
        reservation.setReservationEnd(body.getReservationEnd());

        reservationRepo.save(reservation);

        return ResponseEntity.ok(true);
    }


    public List<Reservation> getReservationsByCustomer(String username) {
        Customer customer = customerRepo.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with this USERNAME is found"));
        return reservationRepo.findByCustomer(customer);
    }

    public void deleteReservation(int id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No reservation with this ID is found"));
        reservationRepo.delete(reservation);
    }

    private double calculateTotalPrice(Activity activity, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        double pricePerHour = activity.getPrice();
        double durationInHours = Duration.between(reservationStart, reservationEnd).toHours();
        return pricePerHour * durationInHours;
    }

}
