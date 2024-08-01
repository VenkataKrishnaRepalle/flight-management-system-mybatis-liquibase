package com.learning.fmsmybatisliquibase.dto;

import com.learning.fmsmybatisliquibase.model.Flight;
import com.learning.fmsmybatisliquibase.model.FlightOperationDay;
import com.learning.fmsmybatisliquibase.model.Seats;
import com.learning.fmsmybatisliquibase.model.Stops;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightResponseDto {
    private Flight flight;

    private List<FlightOperationDay> flightOperationDays;

    private List<Stops> stops;

    private List<Seats> seats;
}