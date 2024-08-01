package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.model.Airport;

import java.util.List;
import java.util.UUID;

public interface AirportService {
    List<Airport> getAll();

    Airport getById(UUID airportId);

    Airport create(Airport airport);

    Airport update(UUID airportId, Airport airport);

    void delete(UUID airportId);
}
