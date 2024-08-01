package com.learning.fmsmybatisliquibase.dao;

import com.learning.fmsmybatisliquibase.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface UserRoleDao {

    List<UserRole> getByUserUuid(@Param("userUuid") UUID userUuid);

    int insert(@Param("userRole") UserRole userRole);

    int delete(@Param("userRole") UserRole userRole);
}
