package com.statista.code.challenge.domain;

import com.statista.code.challenge.usecases.booking.create.CreateBookingCommand;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

public record Booking(
        @NotNull String id,
        @NotNull String description,
        @NotNull BigDecimal price,
        @NotNull Currency currency,
        @NotNull LocalDateTime subscriptionDate,
        @NotNull String email,
        @NotNull Department department
) {
    public static Booking from(CreateBookingCommand command) {
        return new Booking(
                UUID.randomUUID().toString(),
                command.getDescription(),
                command.getPrice(),
                command.getCurrency(),
                command.getSubscriptionStartDate(),
                command.getEmail(),
                command.getDepartment()
        );
    }
}
