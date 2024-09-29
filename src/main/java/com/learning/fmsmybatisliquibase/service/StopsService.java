package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.model.Stops;

import java.util.List;
import java.util.UUID;

public interface StopsService {
    List<Stops> create(UUID flightId, List<Stops> stops);

    List<Stops> getByFlightId(UUID flightId);
}
