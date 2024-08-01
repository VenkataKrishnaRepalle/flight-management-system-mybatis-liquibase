package com.learning.fmsmybatisliquibase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String email;

    private String password;

    private String confirmPassword;
}
