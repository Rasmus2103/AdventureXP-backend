package com.example.adventurexp.adventure.service;

import com.example.adventurexp.adventure.dto.ReservationResponse;
import com.example.adventurexp.adventure.dto.ShiftRequest;
import com.example.adventurexp.adventure.dto.ShiftResponse;
import com.example.adventurexp.adventure.entity.Activity;
import com.example.adventurexp.adventure.entity.Employee;
import com.example.adventurexp.adventure.entity.Shift;
import com.example.adventurexp.adventure.repository.ActivityRepo;
import com.example.adventurexp.adventure.repository.EmployeeRepo;
import com.example.adventurexp.adventure.repository.ShiftRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftService {

    ShiftRepo shiftRepo;
    EmployeeRepo employeeRepo;
    ActivityRepo activityRepo;

    public ShiftService(ShiftRepo shiftRepo, EmployeeRepo employeeRepo, ActivityRepo activityRepo) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepo;
        this.activityRepo = activityRepo;
    }

    public List<ShiftResponse> getShifts(boolean includeAll) {
        List<Shift> shifts = shiftRepo.findAll();
        return shifts.stream().map(shift -> new ShiftResponse(shift, includeAll)).toList();

    }

    public ShiftResponse addShift(ShiftRequest body, boolean includeAll) {
        if(body.getShiftStart().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in past not allowed");
        }
        Employee employee = employeeRepo.findById(body.getEmployeeUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with this USERNAME is found"));

        Activity activity = activityRepo.findById(body.getActivityId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No activity with this ID is found"));

        List<Shift> shifts = employee.getShifts();
        for (Shift shift : shifts) {
            if (shift.getShiftStart().isBefore(body.getShiftEnd()) && shift.getShiftEnd().isAfter(body.getShiftStart())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee already has a shift at this time");
            }
        }

        Shift shift = new Shift(employee, activity, body.getShiftStart(), body.getShiftEnd());
        shift = shiftRepo.save(shift);
        return new ShiftResponse(shift, includeAll);
    }

    public ResponseEntity<Boolean> editShift(ShiftRequest body, int id) {
        Shift shift = getShiftById(id);

        Employee employee = employeeRepo.findByUsername(body.getEmployeeUsername());
        Activity activity = activityRepo.findById(body.getActivityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid activity ID"));

        shift.setEmployee(employee);
        shift.setActivity(activity);
        shift.setShiftStart(body.getShiftStart());
        shift.setShiftEnd(body.getShiftEnd());

        shiftRepo.save(shift);

        return ResponseEntity.ok(true);
    }

    public void deleteShift(int id) {
        Shift shift = getShiftById(id);
        shiftRepo.delete(shift);
    }

    public ShiftResponse findById(int id) {
        Shift shift = getShiftById(id);
        return new ShiftResponse(shift, true);
    }

    private Shift getShiftById(int id) {
        return shiftRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No shift with this ID is found"));
    }
}
