package com.learning.fmsmybatisliquibase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    private UUID uuid;

    private UUID flightUuid;

    private UUID userUuid;

    private UUID seatUuid;

    private Instant bookingTime;

    private BookingStatus status;

    private Instant createdTime;

    private Instant updatedTime;
}