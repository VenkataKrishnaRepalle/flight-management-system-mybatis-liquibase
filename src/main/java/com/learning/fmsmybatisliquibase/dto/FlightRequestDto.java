package com.learning.fmsmybatisliquibase.dto;

import com.learning.fmsmybatisliquibase.model.Flight;
import com.learning.fmsmybatisliquibase.model.OperationDayType;
import com.learning.fmsmybatisliquibase.model.SeatType;
import com.learning.fmsmybatisliquibase.model.Stops;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightRequestDto {

    private Flight flight;

    private List<Stops> stops;

    private List<Map<SeatType, Map<String, Integer>>> seating;

    private List<OperationDayType> operationDayTypes;
}
