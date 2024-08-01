package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.Stops;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface StopsDao {

    List<Stops> getByFlightUuid(@Param("flightUuid") UUID flightUuid);

    Stops getById(@Param("uuid") UUID uuid);

    int insert(@Param("stops") Stops stops);

    int update(@Param("stops") Stops stops);

    int delete(@Param("uuid") UUID uuid);
}
