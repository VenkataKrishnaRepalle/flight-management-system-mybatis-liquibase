package com.learning.fmsmybatisliquibase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightOperationDay {

    private UUID uuid;

    private UUID flightUuid;

    private OperationDayType operationDay;
}
