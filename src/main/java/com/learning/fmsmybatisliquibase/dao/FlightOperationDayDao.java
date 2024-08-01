package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.FlightOperationDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface FlightOperationDayDao {

    List<FlightOperationDay> getByFlightUuid(@Param("flightUuid") UUID flightUuid);

    int insert(@Param("flightOperationDay") FlightOperationDay flightOperationDay);

    int delete(@Param("flightOperationDay") FlightOperationDay flightOperationDay);
}
