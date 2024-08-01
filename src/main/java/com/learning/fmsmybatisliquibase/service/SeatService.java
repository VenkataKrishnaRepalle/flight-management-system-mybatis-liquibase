package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.model.SeatType;
import com.learning.fmsmybatisliquibase.model.Seats;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SeatService {
    List<Seats> create(UUID flightUuid, List<Map<SeatType, Map<String, Integer>>> seating);

    List<Seats> getByFlightId(UUID flightId);

    Seats getById(UUID seatId);

    List<Seats> update(UUID flightId, List<Seats> seats);
}
