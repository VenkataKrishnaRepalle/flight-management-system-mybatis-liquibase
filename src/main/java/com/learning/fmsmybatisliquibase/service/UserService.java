package com.learning.fmsmybatisliquibase.service;

import com.learning.fmsmybatisliquibase.dto.JwtAuthResponseDto;
import com.learning.fmsmybatisliquibase.dto.LoginDto;
import com.learning.fmsmybatisliquibase.dto.RegisterDto;
import com.learning.fmsmybatisliquibase.model.User;

public interface UserService {
    JwtAuthResponseDto login(LoginDto loginDto);

    JwtAuthResponseDto register(RegisterDto registerDto);

    User getMe();
}
