package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.repository.ReservationRepo;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    ReservationRepo reservationRepo;

    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

}
