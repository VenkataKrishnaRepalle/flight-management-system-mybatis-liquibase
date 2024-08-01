package com.learning.fmsmybatisliquibase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private UUID uuid;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String email;

    @JsonIgnore
    private String password;

    private Instant createdTime;

    private Instant updatedTime;
}
