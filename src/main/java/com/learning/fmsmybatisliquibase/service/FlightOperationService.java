package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.model.FlightOperationDay;
import com.learning.fmsmybatisliquibase.model.OperationDayType;

import java.util.List;
import java.util.UUID;

public interface FlightOperationService {
    List<FlightOperationDay> create(UUID uuid, List<OperationDayType> operationDayTypes);

    List<FlightOperationDay> getByFlightId(UUID flightId);
}
