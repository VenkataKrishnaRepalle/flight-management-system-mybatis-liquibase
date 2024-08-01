package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dao.FlightDao;
import com.learning.fmsmybatisliquibase.dao.FlightOperationDayDao;
import com.learning.fmsmybatisliquibase.exception.IntegrityException;
import com.learning.fmsmybatisliquibase.model.FlightOperationDay;
import com.learning.fmsmybatisliquibase.model.OperationDayType;
import com.learning.fmsmybatisliquibase.service.FlightOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightOperationServiceImpl implements FlightOperationService {

    private final FlightOperationDayDao flightOperationDayDao;
    private final FlightDao flightDao;


    @Override
    public List<FlightOperationDay> create(UUID uuid, List<OperationDayType> operationDayTypes) {
        List<FlightOperationDay> operationDays = new ArrayList<>();
        for (var operationDayType : operationDayTypes) {
            var flightOperationDay = FlightOperationDay.builder()
                    .uuid(UUID.randomUUID())
                    .flightUuid(uuid)
                    .operationDay(operationDayType)
                    .build();
            insert(flightOperationDay);
            operationDays.add(flightOperationDay);
        }
        return operationDays;
    }

    @Override
    public List<FlightOperationDay> getByFlightId(UUID flightId) {
        return flightOperationDayDao.getByFlightUuid(flightId);
    }

    private void insert(FlightOperationDay flightOperationDay) {
        try {
            if (0 == flightOperationDayDao.insert(flightOperationDay)) {
                throw new IntegrityException("FLIGHT_OPERATION_DAY_NOT_CREATED", "Flight operation day not created");
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("FLIGHT_OPERATION_DAY_NOT_CREATED", exception.getCause().getMessage());
        }
    }
}
