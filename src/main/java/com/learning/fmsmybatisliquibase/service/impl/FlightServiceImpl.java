package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dao.FlightDao;
import com.learning.fmsmybatisliquibase.dto.FlightRequestDto;
import com.learning.fmsmybatisliquibase.dto.FlightResponseDto;
import com.learning.fmsmybatisliquibase.exception.FoundException;
import com.learning.fmsmybatisliquibase.exception.IntegrityException;
import com.learning.fmsmybatisliquibase.exception.NotFoundException;
import com.learning.fmsmybatisliquibase.model.Flight;
import com.learning.fmsmybatisliquibase.model.Seats;
import com.learning.fmsmybatisliquibase.model.Stops;
import com.learning.fmsmybatisliquibase.service.FlightOperationService;
import com.learning.fmsmybatisliquibase.service.FlightService;
import com.learning.fmsmybatisliquibase.service.SeatService;
import com.learning.fmsmybatisliquibase.service.StopsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;

    private final StopsService stopsService;

    private final FlightOperationService flightOperationService;

    private final SeatService seatService;

    @Override
    public List<Flight> getAll() {
        return flightDao.getAll();
    }

    @Override
    public FlightResponseDto create(FlightRequestDto flightRequestDto) {
        var isFlightExists = flightDao.getByNumber(flightRequestDto.getFlight().getNumber());

        if (isFlightExists != null) {
            throw new FoundException("FLIGHT_ALREADY_EXISTS", "Flight already exists with number: " + flightRequestDto.getFlight().getNumber());
        }

        var savedFlight = insert(flightRequestDto.getFlight());

        var stops = stopsService.create(savedFlight.getUuid(), flightRequestDto.getStops());

        var flightOperations = flightOperationService.create(savedFlight.getUuid(), flightRequestDto.getOperationDayTypes());

        var seats = seatService.create(savedFlight.getUuid(), flightRequestDto.getSeating());
        return FlightResponseDto.builder()
                .flight(savedFlight)
                .flightOperationDays(flightOperations)
                .seats(seats.stream().sorted(Comparator.comparing(Seats::getSeatNumber)).toList())
                .stops(stops.stream().sorted(Comparator.comparing(Stops::getStopSequence)).toList())
                .build();
    }

    private Flight insert(Flight flight) {
        flight.setUuid(UUID.randomUUID());
        flight.setCreatedTime(Instant.now());
        flight.setUpdatedTime(Instant.now());
        try {
            if (0 == flightDao.insert(flight)) {
                throw new IntegrityException("FLIGHT_NOT_CREATED", "Flight not created");
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("FLIGHT_NOT_CREATED", exception.getCause().getMessage());
        }
        return flight;
    }

    @Override
    public FlightResponseDto getById(UUID flightId) {
        var flight = get(flightId);
        var operationDays = flightOperationService.getByFlightId(flightId);
        var stops = stopsService.getByFlightId(flightId);
        var seats = seatService.getByFlightId(flightId);

        return FlightResponseDto.builder()
                .flight(flight)
                .flightOperationDays(operationDays)
                .seats(seats.isEmpty() ? null : seats.stream().sorted(Comparator.comparing(Seats::getSeatNumber)).toList())
                .stops(stops.isEmpty() ? null : stops.stream().sorted(Comparator.comparing(Stops::getStopSequence)).toList())
                .build();
    }

    private Flight get(UUID flightId) {
        var flight = flightDao.getById(flightId);
        if (flight == null) {
            throw new NotFoundException("FLIGHT_NOT_FOUND", "Flight not found with id: " + flightId);
        }
        return flight;
    }

    @Override
    public void delete(UUID flightId) {
        getById(flightId);

        try {
            if (0 == flightDao.delete(flightId)) {
                throw new IntegrityException("FLIGHT_NOT_DELETED", "Flight not deleted with id: " + flightId);
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("FLIGHT_NOT_DELETED", exception.getCause().getMessage());
        }
    }

    @Override
    public FlightResponseDto update(UUID flightId, FlightRequestDto flightRequestDto) {
        var flight = get(flightId);
        var updatedFlight = update(flight);
        return null;
    }

    private Flight update(Flight flight) {
        flight.setUpdatedTime(Instant.now());
        try {
            if (0 == flightDao.update(flight)) {
                throw new IntegrityException("FLIGHT_NOT_UPDATED", "Flight not updated with id: " + flight.getUuid());
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("FLIGHT_NOT_UPDATED", exception.getCause().getMessage());
        }
        return flight;
    }
}