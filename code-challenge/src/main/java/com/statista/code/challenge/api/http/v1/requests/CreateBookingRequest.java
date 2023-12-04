package com.statista.code.challenge.api.http.v1.requests;

import com.statista.code.challenge.domain.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record CreateBookingRequest(
        @NotNull
        @NotEmpty
        String description,
        @NotNull
        BigDecimal price,
        Currency currency,
        @NotNull
        @Future(message = "The Item due date can not be in the past")
        LocalDateTime subscriptionStartDate,
        @Email
        @NotNull
        @NotEmpty
        String email,
        @NotNull
        @NotEmpty
        Department department
) {
}