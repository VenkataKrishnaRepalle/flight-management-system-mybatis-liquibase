package com.learning.fmsmybatisliquibase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stops {

    private UUID uuid;

    private UUID flightUuid;

    private UUID airportUuid;

    private int stopSequence;

    private StopStatus stopStatus;

    private LocalTime arrivalTime;

    private LocalTime departureTime;
}