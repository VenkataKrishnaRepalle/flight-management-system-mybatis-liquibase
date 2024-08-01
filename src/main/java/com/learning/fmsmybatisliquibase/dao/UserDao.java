package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

public interface UserDao {

    User findByEmail(@Param("email") String email);

    User getById(@Param("uuid") UUID uuid);

    int insert(@Param("user") User user);

    int update(@Param("user") User user);

    int delete(@Param("uuid") UUID uuid);

    Long count();
}
