package com.statista.code.challenge.usecases.booking.create;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.domain.Department;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
public class CreateBookingCommand {
    private final String description;
    private final BigDecimal price;
    private final Currency currency;
    private final LocalDateTime subscriptionStartDate;
    private final String email;
    private final Department department;

    private CreateBookingCommand(
            String description,
            BigDecimal price,
            Currency currency,
            LocalDateTime subscriptionStartDate,
            String email,
            Department department
    ) {
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.subscriptionStartDate = subscriptionStartDate;
        this.email = email;
        this.department = department;
    }

    public static CreateBookingCommand fromRequest(@NotNull CreateBookingRequest request) {
        return new CreateBookingCommand(
                request.description(),
                request.price(),
                request.currency(),
                request.subscriptionStartDate(),
                request.email(),
                request.department()
        );
    }
}
