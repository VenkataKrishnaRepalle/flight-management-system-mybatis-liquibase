package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.dto.CreateFlightRequestDto;
import com.learning.fmsmybatisliquibase.dto.FlightResponseDto;
import com.learning.fmsmybatisliquibase.model.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightService {
    List<Flight> getAll();

    FlightResponseDto create(CreateFlightRequestDto createFlightRequestDto);

    FlightResponseDto getById(UUID flightId);
}
