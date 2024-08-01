package com.learning.fmsmybatisliquibase.service.impl;

import com.learning.fmsmybatisliquibase.dto.JwtAuthResponseDto;
import com.learning.fmsmybatisliquibase.dto.LoginDto;
import com.learning.fmsmybatisliquibase.dto.RegisterDto;
import com.learning.fmsmybatisliquibase.exception.IntegrityException;
import com.learning.fmsmybatisliquibase.model.RoleType;
import com.learning.fmsmybatisliquibase.model.User;
import com.learning.fmsmybatisliquibase.model.UserRole;
import com.learning.fmsmybatisliquibase.exception.FoundException;
import com.learning.fmsmybatisliquibase.exception.InvalidInputException;
import com.learning.fmsmybatisliquibase.exception.NotFoundException;
import com.learning.fmsmybatisliquibase.mapper.UserMapper;
import com.learning.fmsmybatisliquibase.dao.UserDao;
import com.learning.fmsmybatisliquibase.dao.UserRoleDao;
import com.learning.fmsmybatisliquibase.security.JwtTokenProvider;
import com.learning.fmsmybatisliquibase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserMapper userMapper;

    @Override
    public JwtAuthResponseDto login(LoginDto loginDto) {
        var user = userDao.findByEmail(loginDto.getEmail().trim());

        if (user == null) {
            throw new NotFoundException("USER_NOT_FOUND", "User not found with email: " + loginDto.getEmail());
        }

        if (!passwordEncoder.matches(loginDto.getPassword().trim(), user.getPassword())) {
            throw new InvalidInputException("INCORRECT_PASSWORD", "Entered password is incorrect");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUuid(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtTokenProvider.generateToken(authentication);

        var userRoles = userRoleDao.getByUserUuid(user.getUuid());
        System.out.println(userRoles);
        var roles = userRoles
                .stream().map(userRole -> userRole.getRole().toString())
                .toList();

        return JwtAuthResponseDto.builder()
                .userId(user.getUuid())
                .roles(roles)
                .tokenType("Bearer ")
                .accessToken(token)
                .build();

    }

    @Override
    public JwtAuthResponseDto register(RegisterDto registerDto) {
        var userExistsByEmail = userDao.findByEmail(registerDto.getEmail().trim());
        if (userExistsByEmail != null) {
            throw new FoundException("EMAIL_ALREADY_EXISTS", "User already exists with email: " + registerDto.getEmail());
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new InvalidInputException("INVALID_INPUT", "Passwords are not matched");
        }

        var user = userMapper.registerDtoToUser(registerDto);
        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword().trim()));
        user.setCreatedTime(Instant.now());
        user.setUpdatedTime(Instant.now());

        try {
            if (0 == userDao.insert(user)) {
                throw new IntegrityException("USER_NOT_CREATED", "User not created");
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("USER_NOT_CREATED", "User not created");
        }

        var role = UserRole.builder()
                .userUuid(user.getUuid())
                .role(RoleType.CUSTOMER)
                .build();

        try {
            if (0 == userRoleDao.insert(role)) {
                throw new IntegrityException("USER_ROLE_NOT_CREATED", "User role not created");
            }
        } catch (DataIntegrityViolationException exception) {
            throw new IntegrityException("USER_ROLE_NOT_CREATED", "User role not created");
        }

        return login(LoginDto.builder()
                .email(registerDto.getEmail())
                .password(registerDto.getPassword().trim())
                .build());
    }

    @Override
    public User getMe() {
        var uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        return userDao.getById(uuid);
    }
}
