package com.learning.fmsmybatisliquibase.mapper;

import com.learning.fmsmybatisliquibase.dto.RegisterDto;
import com.learning.fmsmybatisliquibase.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User registerDtoToUser(RegisterDto registerDto);
}
