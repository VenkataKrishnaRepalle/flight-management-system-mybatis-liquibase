package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.Airport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface AirportDao {

    Airport getByCode(@Param("code") String code);

    List<Airport> getAll();

    Airport getById(@Param("uuid") UUID uuid);

    int insert(@Param("airport") Airport airport);

    int update(@Param("airport") Airport airport);

    int delete(@Param("uuid") UUID uuid);
}