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
public class Airport {

    private UUID uuid;

    private String code;

    private String name;

    private String city;

    private String country;

    private double longitude;

    private double latitude;

    private UUID createdBy;

    private Instant createdTime;

    private Instant updatedTime;
}
