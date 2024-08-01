package com.learning.fmsmybatisliquibase.controller;

import com.learning.fmsmybatisliquibase.dto.CreateFlightRequestDto;
import com.learning.fmsmybatisliquibase.dto.FlightResponseDto;
import com.learning.fmsmybatisliquibase.model.Flight;
import com.learning.fmsmybatisliquibase.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<Flight>> getAll() {
        return new ResponseEntity(flightService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponseDto> getById(@PathVariable UUID flightId) {
        return new ResponseEntity<>(flightService.getById(flightId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FlightResponseDto> create(@RequestBody CreateFlightRequestDto createFlightRequestDto) {
        return new ResponseEntity<>(flightService.create(createFlightRequestDto), HttpStatus.CREATED);
    }
}
