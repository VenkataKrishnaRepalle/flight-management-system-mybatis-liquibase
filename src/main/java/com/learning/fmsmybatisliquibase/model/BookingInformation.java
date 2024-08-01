package com.learning.fmsmybatisliquibase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingInformation {

    private UUID uuid;

    private UUID bookingUuid;

    private BookingStatus bookingStatus;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String city;

    private String country;

    private String postalCode;

    private Instant createdTime;

    private Instant updatedTime;
}
