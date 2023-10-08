package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ArrangementRequest;
import com.example.adventurexp.adventure.dto.ArrangementResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Arrangement;
import com.example.adventurexp.adventure.entity.Customer;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.repository.ActivityRepo;
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
    ActivityRepo activityRepo;

    public ArrangementService(ArrangementRepo arrangementRepo, ReservationRepo reservationRepo, CustomerRepo customerRepo, ActivityRepo activityRepo) {
        this.arrangementRepo = arrangementRepo;
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
        this.activityRepo = activityRepo;
    }

    public List<ArrangementResponse> getAllArrangements(boolean includeAll, boolean includeAllCustomer, boolean includeAllActivities) {
        List<Arrangement> arrangements = arrangementRepo.findAll();

        return arrangements.stream().map(arrangement -> new ArrangementResponse(arrangement,
                includeAll,
                includeAllCustomer,
                includeAllActivities)).toList();
    }

    //POST
    public ArrangementResponse createArrangement(ArrangementRequest body) {

        Customer customer = customerRepo.findByUsername(body.getCustomerUsername());
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer");
        }

        List<Reservation> reservations = body.getReservationIds().stream().map(reservationRequest -> {
            Reservation reservation = reservationRepo.findById(reservationRequest.describeConstable().get()).get();
            Activity activity = activityRepo.findById(reservation.getActivity().getId()).get();
            activity.setId(reservation.getActivity().getId());

            reservation.setActivity(activity);
            reservation.setParticipants(reservation.getParticipants());
            reservation.setReservationStart(reservation.getReservationStart());
            reservation.setReservationEnd(reservation.getReservationEnd());


            return reservation;
        }).toList();


        Arrangement arrangement = new Arrangement();
        arrangement.setCustomer(customer);
        arrangement.setReservations(reservations);
        arrangement.setParticipants(body.getParticipants());
        arrangement.setName(body.getName());
        arrangement.setArrangementStart(body.getArrangementStart());
        arrangement.setArrangementEnd(body.getArrangementEnd());
        //TODO set aggregated price

        Arrangement savedArrangement = arrangementRepo.save(arrangement);

        return new ArrangementResponse(savedArrangement, true, false, true);
    }

    //PUT
    public ArrangementResponse editArrangement(int id, ArrangementRequest body) {
        Arrangement arrangement = arrangementRepo.findById(id).get();

        arrangement.setName(body.getName());
        arrangement.setParticipants(body.getParticipants());
        arrangement.setArrangementStart(body.getArrangementStart());
        arrangement.setArrangementEnd(body.getArrangementEnd());

        for (Integer reservationId : body.getReservationIds()) {
            Reservation reservation = reservationRepo.findById(reservationId).get();
            arrangement.addReservation(reservation);
        }

        Arrangement editedArrangement = arrangementRepo.save(arrangement);
        return new ArrangementResponse(editedArrangement, true, false, true);
    }

    public ArrangementResponse findById(int id) {
        Arrangement arrangement = arrangementRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No arrangement with this ID is found"));
        return new ArrangementResponse(arrangement, true, false, true);
    }

    //DELETE
    public void deleteArrangement(int id) {
        arrangementRepo.deleteById(id);
    }
}
