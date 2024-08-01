package com.learning.fmsmybatisliquibase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {

    private UUID uuid;

    private String number;

    private String carrierName;

    private String model;

    private UUID departureAirportUuid;

    private UUID arrivalAirportUuid;

    private int seatCapacity;

    private UUID createdBy;

    private Instant createdTime;

    private Instant updatedTime;
}
