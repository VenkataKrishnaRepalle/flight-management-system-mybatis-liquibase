package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dao.FlightDao;
import com.learning.fmsmybatisliquibase.dto.CreateFlightRequestDto;
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
    public FlightResponseDto create(CreateFlightRequestDto createFlightRequestDto) {
        var isFlightExists = flightDao.getByNumber(createFlightRequestDto.getFlight().getNumber());

        if (isFlightExists != null) {
            throw new FoundException("FLIGHT_ALREADY_EXISTS", "Flight already exists with number: " + createFlightRequestDto.getFlight().getNumber());
        }

        var savedFlight = insert(createFlightRequestDto.getFlight());

        createFlightRequestDto.getStops().forEach(stops -> stops.setFlightUuid(savedFlight.getUuid()));
        var stops = stopsService.create(createFlightRequestDto.getStops());

        var flightOperations = flightOperationService.create(savedFlight.getUuid(), createFlightRequestDto.getOperationDayTypes());

        var seats = seatService.create(savedFlight.getUuid(), createFlightRequestDto.getSeating());
        return FlightResponseDto.builder()
                .flight(savedFlight)
                .flightOperationDays(flightOperations)
                .seats(seats.stream().sorted(Comparator.comparing(Seats::getSeatNumber)).toList())
                .stops(stops.stream().sorted(Comparator.comparing(Stops::getStopSequence)).toList())
                .build();
    }

    @Override
    public FlightResponseDto getById(UUID flightId) {
        var flight = flightDao.getById(flightId);
        if (flight == null) {
            throw new NotFoundException("FLIGHT_NOT_FOUND", "Flight not found with id: " + flightId);
        }

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
}
