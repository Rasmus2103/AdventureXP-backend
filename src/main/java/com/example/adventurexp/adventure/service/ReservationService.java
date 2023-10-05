package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ReservationRequest;
import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.entity.Shift;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.repository.ReservationRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.antlr.v4.runtime.ParserInterpreter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    ReservationRepo reservationRepo;
    CustomerRepo customerRepo;
    ActivityRepo activityRepo;
    ShiftRepo shiftRepo;


    public ReservationService(ReservationRepo reservationRepo, CustomerRepo customerRepo, ActivityRepo activityRepo, ShiftRepo shiftRepo) {
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
        this.activityRepo = activityRepo;
        this.shiftRepo = shiftRepo;
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
        if (body.getReservationStart().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in past not allowed");
        }
        if (body.getReservationStart().isAfter(body.getReservationEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be before end date");
        }
        Customer customer = customerRepo.findByUsername(body.getUsername());
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer");
        }
        Activity activity = activityRepo.findAllById(body.getActivityId());
        if (activity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid activity");
        }
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        String resStart = df.format(body.getReservationStart());
        String resEnd = df.format(body.getReservationEnd());
        String resStart1 = String.format(resStart);
        String resEnd1 = String.format(resEnd);
        int resStart2 = Integer.parseInt(resStart1);
        int resEnd2 = Integer.parseInt(resEnd1);
        Double price = activity.getPrice() * (resStart2 - resEnd2);
        if (price > customer.getCredit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money on account");
        }
        customer.setCredit(customer.getCredit() - price);
        customerRepo.save(customer);


        List<Shift> shifts = shiftRepo.findAllByActivity(activity);
        if (!shifts.isEmpty()) {
            for (Shift s : shifts) {
                if (body.getReservationStart().isBefore(s.getShiftStart()) ||
                        body.getReservationEnd().isAfter(s.getShiftEnd())
                ) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "activity is not open in this period");
                }
            }
        }
        List<Reservation> activityReservations = activity.getReservations();
        if (!activityReservations.isEmpty()) {
            for (Reservation r : activityReservations) {
                if (body.getReservationStart().isBefore(r.getReservationEnd()) &&
                        body.getReservationEnd().isAfter(r.getReservationStart()) ||
                        body.getReservationStart().isEqual(r.getReservationStart()) ||
                        body.getReservationEnd().isEqual(r.getReservationEnd())
                ) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "activity is reserved in this period");
                }
            }
        }

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setParticipants(body.getParticipants());
        reservation.setActivity(activity);
        reservation.setReservationStart(body.getReservationStart());
        reservation.setReservationEnd(body.getReservationEnd());
        reservation.setCreated(LocalDate.now());
        reservation.setEdited(LocalDate.now());

        Reservation savedReservation = reservationRepo.save(reservation);

        return new ReservationResponse(savedReservation, true, false, false);
    }

    public List<Reservation> getReservationsByCustomer(String username) {
        Customer customer = customerRepo.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with this USERNAME is found"));
        return reservationRepo.findByCustomer(customer);
    }

}
