package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.dto.ShiftRequest;
import com.example.adventurexp.adventure.dto.ShiftResponse;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.entity.Reservation;
import com.example.adventurexp.adventure.entity.Shift;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ShiftService {

    ShiftRepo shiftRepo;
    EmployeeRepo employeeRepo;

    public ShiftService(ShiftRepo shiftRepo) {
        this.shiftRepo = shiftRepo;
    }

    public List<ShiftResponse> getShifts(boolean includeAll) {
        List<Shift> shifts = shiftRepo.findAll();

        List<ShiftResponse> shiftResponses = shifts.stream().map(shift -> new ShiftResponse(shift, includeAll)).toList();

        return shiftResponses;
    }

    public ShiftResponse addShift(ShiftRequest body, boolean includeAll) {
        if(body.getShiftStart().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in past not allowed");
        }
        Employee employee = employeeRepo.findById(body.getEmployeeUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with this USERNAME is found"));

        Shift shift = new Shift(employee, body.getShiftStart(), body.getShiftEnd());
        shift = shiftRepo.save(shift);
        return new ShiftResponse(shift, includeAll);
    }
}
