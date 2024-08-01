package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.Seats;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface SeatsDao {

    List<Seats> getByFlightUuid(@Param("flightUuid") UUID flightUuid);

    Seats getById(@Param("uuid") UUID uuid);

    Seats getByFlightUuidAndSeatNumber(@Param("flightUuid") UUID flightUuid,
                                       @Param("seatNumber") String seatNumber);

    int insert(@Param("seats") Seats seats);

    int update(@Param("seats") Seats seats);

    int delete(@Param("uuid") UUID uuid);
}
