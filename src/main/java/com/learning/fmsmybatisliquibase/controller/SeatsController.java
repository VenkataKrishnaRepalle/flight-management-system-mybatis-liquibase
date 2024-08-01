package com.learning.fmsmybatisliquibase.controller;

import com.learning.fmsmybatisliquibase.model.Seats;
import com.learning.fmsmybatisliquibase.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seat")
@RequiredArgsConstructor
public class SeatsController {

    private final SeatService seatService;

    @GetMapping("getByFlightId/{flightId}")
    public ResponseEntity<List<Seats>> getByFlightId(@PathVariable UUID flightId) {
        return new ResponseEntity<>(seatService.getByFlightId(flightId), HttpStatus.OK);
    }

    @GetMapping("getById/{seatId}")
    public ResponseEntity<Seats> getById(@PathVariable UUID seatId) {
        return new ResponseEntity<>(seatService.getById(seatId), HttpStatus.OK);
    }

    @PutMapping("{flightId}")
    public ResponseEntity<List<Seats>> update(@PathVariable UUID flightId, @RequestBody List<Seats> seats) {
        return new ResponseEntity<>(seatService.update(flightId, seats), HttpStatus.OK);
    }
}
