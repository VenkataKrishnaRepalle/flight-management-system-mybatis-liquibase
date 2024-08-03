package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dao.StopsDao;
import com.learning.fmsmybatisliquibase.exception.IntegrityException;
import com.learning.fmsmybatisliquibase.model.Stops;
import com.learning.fmsmybatisliquibase.service.StopsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StopsServiceImpl implements StopsService {

    private final StopsDao stopsDao;

    @Override
    public List<Stops> create(List<Stops> stops) {
        List<Stops> stopsResult = new ArrayList<>();
        for(var stop: stops) {
            if(stop.getArrivalTime().isAfter(stop.getDepartureTime())){
                throw new IntegrityException("INVALID_STOP_TIMES", "Arrival time cannot be before departure time");
            }
        }
        for (var stop : stops) {
            stopsResult.add(insert(stop));
        }
        return stopsResult.isEmpty()? null : stopsResult;
    }

    @Override
    public List<Stops> getByFlightId(UUID flightId) {
        return stopsDao.getByFlightUuid(flightId);
    }

    private Stops insert(Stops stops) {
        stops.setUuid(UUID.randomUUID());
        try {
            if (0 == stopsDao.insert(stops)) {
                throw new IntegrityException("STOP_NOT_INSERTED", "Stop not created");
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("STOP_NOT_INSERTED", exception.getCause().getMessage());
        }
        return stops;
    }
}
