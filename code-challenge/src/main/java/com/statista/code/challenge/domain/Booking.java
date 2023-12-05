package com.statista.code.challenge.domain;

import com.statista.code.challenge.usecases.booking.create.CreateBookingCommand;
import com.statista.code.challenge.usecases.booking.upsert.UpdateBookingCommand;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

public record Booking(
        @NotNull String id,
        @NotNull String description,
        @NotNull BigDecimal price,
        @NotNull Currency currency,
        @NotNull Date subscriptionDate,
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

    public static Booking from(UpdateBookingCommand command) {
        return new Booking(
                command.getId(),
                command.getDescription(),
                command.getPrice(),
                command.getCurrency(),
                command.getSubscriptionStartDate(),
                command.getEmail(),
                command.getDepartment()
        );
    }
}
