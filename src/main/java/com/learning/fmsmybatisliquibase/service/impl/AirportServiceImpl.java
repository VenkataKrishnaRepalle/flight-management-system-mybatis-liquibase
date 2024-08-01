package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dao.AirportDao;
import com.learning.fmsmybatisliquibase.exception.IntegrityException;
import com.learning.fmsmybatisliquibase.model.Airport;
import com.learning.fmsmybatisliquibase.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportDao airportDao;

    @Override
    public List<Airport> getAll() {
        return airportDao.getAll();
    }

    @Override
    public Airport getById(UUID airportId) {
        var airport = airportDao.getById(airportId);
        if (airport == null) {
            throw new IntegrityException("AIRPORT_DETAILS_NOT_FOUND", "Airport Details not found with id: " + airportId);
        }
        return airport;
    }

    @Override
    public Airport create(Airport airport) {
        var airportExistsByCode = airportDao.getByCode(airport.getCode());
        if (airportExistsByCode != null) {
            throw new IntegrityException("AIRPORT_CODE_ALREADY_EXISTS", "Airport already exists with code: " + airport.getCode());
        }
        airport.setUuid(UUID.randomUUID());
        airport.setCreatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        airport.setCreatedTime(Instant.now());
        airport.setUpdatedTime(Instant.now());

        try {
            if (0 == airportDao.insert(airport)) {
                throw new IntegrityException("AIRPORT_NOT_CREATED", "Airport creation failed");
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("AIRPORT_NOT_CREATED", exception.getCause().getMessage());
        }

        return airport;
    }

    @Override
    public Airport update(UUID airportId, Airport airport) {
        getById(airport.getUuid());
        airport.setUpdatedTime(Instant.now());

        try {
            if (0 == airportDao.update(airport)) {
                throw new IntegrityException("AIRPORT_NOT_UPDATED", "Airport not updated for airportId: " + airportId);
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("AIRPORT_NOT_UPDATED", exception.getCause().getMessage());
        }

        return airport;
    }

    @Override
    public void delete(UUID airportId) {
        getById(airportId);

        try {
            if (0 == airportDao.delete(airportId)) {
                throw new IntegrityException("AIRPORT_NOT_DELETED", "Airport not deleted for airportId: " + airportId);
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("AIRPORT_NOT_DELETED", exception.getCause().getMessage());
        }
    }
}
