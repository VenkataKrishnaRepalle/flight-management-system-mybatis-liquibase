package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dao.SeatsDao;
import com.learning.fmsmybatisliquibase.exception.IntegrityException;
import com.learning.fmsmybatisliquibase.model.SeatType;
import com.learning.fmsmybatisliquibase.model.Seats;
import com.learning.fmsmybatisliquibase.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatsDao seatsDao;

    @Override
    public List<Seats> create(UUID flightUuid, List<Map<SeatType, Map<String, Integer>>> seatings) {

        List<Seats> seats = new ArrayList<>();
        for (Map<SeatType, Map<String, Integer>> seating : seatings) {
            for (Map.Entry<SeatType, Map<String, Integer>> entry : seating.entrySet()) {
                var seatType = entry.getKey();
                var seatRanges = entry.getValue();
                for (Map.Entry<String, Integer> seatRange : seatRanges.entrySet()) {
                    var seatNames = seatRange.getKey();
                    var seatCount = seatRange.getValue();

                    var seatSets = processSeats(seatNames, seatCount);
                    for (var seatSet : seatSets) {
                        var seat = Seats.builder()
                                .uuid(UUID.randomUUID())
                                .flightUuid(flightUuid)
                                .seatType(seatType)
                                .seatNumber(seatSet)
                                .build();
                        insert(seat);
                        seats.add(seat);
                    }
                }
            }
        }
        return seats;
    }

    @Override
    public List<Seats> getByFlightId(UUID flightId) {
        return seatsDao.getByFlightUuid(flightId);
    }

    @Override
    public Seats getById(UUID seatId) {
        var seat = seatsDao.getById(seatId);
        if (seat == null) {
            throw new IntegrityException("SEAT_NOT_FOUND", "Seat not found with id: " + seatId);
        }
        return seat;
    }

    @Override
    public List<Seats> update(UUID flightId, List<Seats> seats) {
        return seats.stream()
                .peek(seat -> {
                    getById(seat.getUuid());
                    if (seatsDao.getByFlightUuidAndSeatNumber(flightId, seat.getSeatNumber()) != null) {
                        throw new IntegrityException("SEAT_EXISTS", "Seat already exists with number " + seat.getSeatNumber());

                    }
                })
                .map(seat -> {
                    try {
                        if (0 == seatsDao.update(seat)) {
                            throw new IntegrityException("SEAT_NOT_UPDATED", "Seat not updated for seat " + seat.getSeatNumber());
                        }
                        return seat;
                    } catch (DataIntegrityViolationException exception) {
                        throw new IntegrityException("SEAT_NOT_UPDATED", exception.getCause().getMessage());
                    }
                }).toList();
    }

    private Set<String> processSeats(String seatNames, int seatCount) {
        Set<String> seatSet = new HashSet<>();
        var seats = Objects.requireNonNull(seatNames).split("-");
        for (char c = seats[0].charAt(0); c <= seats[1].charAt(0); c++) {
            for (int i = 1; i <= seatCount; i++) {
                seatSet.add(String.valueOf(c) + i);
            }
        }
        return seatSet;
    }

    private void insert(Seats seat) {
        try {
            if (0 == seatsDao.insert(seat)) {
                throw new IntegrityException("SEATS_NOT_CREATED", "Seats created for seat " + seat.getSeatNumber());
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("SEATS_NOT_CREATED", exception.getCause().getMessage());
        }
    }
}
