package com.learning.fmsmybatisliquibase.exception.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private StatusType status = StatusType.ERROR;
    private Error error;
}
