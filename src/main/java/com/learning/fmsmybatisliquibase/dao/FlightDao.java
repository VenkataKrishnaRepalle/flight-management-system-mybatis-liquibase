package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.Flight;
import com.learning.fmsmybatisliquibase.service.FlightService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface FlightDao {
    List<Flight> getAll();

    Flight getByNumber(@Param("number") String number);

    Flight getById(@Param("uuid") UUID id);

    int insert(@Param("flight") Flight flight);

    int update(@Param("flight") Flight flight);

    int delete(@Param("uuid") UUID uuid);
}
