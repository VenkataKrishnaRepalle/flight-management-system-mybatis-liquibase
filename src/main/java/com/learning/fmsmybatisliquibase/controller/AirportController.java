package com.learning.fmsmybatisliquibase.controller;

import com.learning.fmsmybatisliquibase.model.Airport;
import com.learning.fmsmybatisliquibase.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<Airport>> getAll() {
        return new ResponseEntity<>(airportService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{airportId}")
    public ResponseEntity<Airport> getById(@PathVariable UUID airportId) {
        return new ResponseEntity<>(airportService.getById(airportId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Airport> create(@RequestBody Airport airport) {
        return new ResponseEntity<>(airportService.create(airport), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{airportId}")
    public ResponseEntity<Airport> update(@PathVariable UUID airportId, @RequestBody Airport airport) {
        return new ResponseEntity<>(airportService.update(airportId, airport), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{airportId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID airportId) {
        airportService.delete(airportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
