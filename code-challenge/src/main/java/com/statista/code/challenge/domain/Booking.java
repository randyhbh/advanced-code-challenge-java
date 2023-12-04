package com.statista.code.challenge.domain;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record Booking(
        @NotNull String id,
        @NotNull String description,
        @NotNull BigDecimal price,
        @NotNull Currency currency,
        @NotNull LocalDateTime subscriptionDate,
        @NotNull String email,
        @NotNull Department department
) {
}
