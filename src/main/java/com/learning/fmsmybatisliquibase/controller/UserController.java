package com.learning.fmsmybatisliquibase.controller;

import com.learning.fmsmybatisliquibase.dto.JwtAuthResponseDto;
import com.learning.fmsmybatisliquibase.dto.LoginDto;
import com.learning.fmsmybatisliquibase.dto.RegisterDto;
import com.learning.fmsmybatisliquibase.model.User;
import com.learning.fmsmybatisliquibase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getMe")
    public ResponseEntity<User> getMe() {
        return new ResponseEntity<>(userService.getMe(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponseDto> register(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(userService.register(registerDto), HttpStatus.CREATED);
    }
}
