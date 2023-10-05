package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ArrangementRequest;
import com.example.adventurexp.adventure.dto.ArrangementResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Arrangement;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.repository.ArrangementRepo;
import com.example.adventurexp.adventure.repository.CustomerRepo;
import com.example.adventurexp.adventure.repository.ReservationRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class ArrangementService {

    ArrangementRepo arrangementRepo;
    ReservationRepo reservationRepo;
    CustomerRepo customerRepo;

    public ArrangementService(ArrangementRepo arrangementRepo, ReservationRepo reservationRepo, CustomerRepo customerRepo) {
        this.arrangementRepo = arrangementRepo;
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
    }

    //GET

    public ArrangementResponse getArrangementById (int id) {
        try {
            Arrangement arrangement = arrangementRepo.findArrangementById(id);
            return new ArrangementResponse(arrangement, true, true, true);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No arrangement with this ID is found");
        }
    }

    public ArrangementResponse getArrangementByName(String name) {
      try {
            Arrangement arrangement = arrangementRepo.findArrangementByName(name);
            return new ArrangementResponse(arrangement, true, true, true);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No arrangement with this NAME is found");
        }
    }

    public List<ArrangementResponse> getAllArrangements(boolean includeAll, boolean includeAllCustomer, boolean includeAllActivities) {
        List<Arrangement> arrangements = arrangementRepo.findAll();

        List<ArrangementResponse> response = arrangements.stream().map(arrangement -> new ArrangementResponse(arrangement,
                includeAll,
                includeAllCustomer,
                includeAllActivities)).toList();

        return response;
    }

    //POST
    public ArrangementResponse createArrangement(ArrangementRequest body) {

        Customer customer = customerRepo.findByUsername(body.getCustomerUsername());
        if(customer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer");
        }

        List<Reservation> reservations = body.getReservations().stream().map(reservationRequest -> {
            Activity activity = new Activity();
            activity.setId(reservationRequest.getActivityId());
            Reservation reservation = new Reservation();
            reservation.setActivity(activity);
            reservation.setParticipants(reservationRequest.getParticipants());
            reservation.setReservationStart(reservationRequest.getReservationStart());
            reservation.setReservationEnd(reservationRequest.getReservationEnd());
            return reservation;
        }).toList();



        Arrangement arrangement = new Arrangement();
        arrangement.setCustomer(customer);
        arrangement.setReservations(reservations);
        arrangement.setParticipants(body.getParticipants());
        Arrangement savedArrangement = arrangementRepo.save(arrangement);

        return new ArrangementResponse(savedArrangement, true, true, true);
    }

    //PUT
    public ArrangementResponse editArrangement(int id, ArrangementRequest body, List<Integer> reservationIds) {
        Arrangement arrangement = arrangementRepo.findById(id).get();

        arrangement.setName(body.getName());
        arrangement.setParticipants(body.getParticipants());
        arrangement.getReservations().clear();

        List<Reservation> reservations = body.getReservations().stream()
                .map(reservationRequest -> reservationRepo.findById(reservationIds.iterator().next()).get())
                .toList();

        reservations.forEach(arrangement::addReservation);

        Arrangement editedArrangement = arrangementRepo.save(arrangement);
        return new ArrangementResponse(editedArrangement, true, true, true);
    }

    //DELETE
    public void deleteArrangement(int id) {
        arrangementRepo.deleteById(id);
    }
}
