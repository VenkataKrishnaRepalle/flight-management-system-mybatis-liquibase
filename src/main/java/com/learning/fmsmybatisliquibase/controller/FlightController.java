package com.learning.fmsmybatisliquibase.controller;

import com.learning.fmsmybatisliquibase.dto.FlightRequestDto;
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
        return ResponseEntity.ok()
                .body(flightService.getAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponseDto> getById(@PathVariable UUID flightId) {
        return ResponseEntity.ok()
                .body(flightService.getById(flightId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FlightResponseDto> create(@RequestBody FlightRequestDto flightRequestDto) {
        return new ResponseEntity<>(flightService.create(flightRequestDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{flightId}")
    public ResponseEntity<FlightResponseDto> update(@PathVariable UUID flightId, @RequestBody FlightRequestDto flightRequestDto) {
        return new ResponseEntity<>(flightService.update(flightId, flightRequestDto), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{flightId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID flightId) {
        flightService.delete(flightId);
        return ResponseEntity.noContent().build();
    }
}
