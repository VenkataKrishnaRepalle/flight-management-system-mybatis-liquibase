package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.dto.FlightRequestDto;
import com.learning.fmsmybatisliquibase.dto.FlightResponseDto;
import com.learning.fmsmybatisliquibase.model.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightService {
    List<Flight> getAll();

    FlightResponseDto create(FlightRequestDto flightRequestDto);

    FlightResponseDto getById(UUID flightId);

    void delete(UUID flightId);

    FlightResponseDto update(UUID flightId, FlightRequestDto flightRequestDto);
}
