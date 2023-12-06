package com.statista.code.challenge.usecases.booking.upsert;

import com.statista.code.challenge.api.http.v1.requests.UpdateBookingRequest;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.util.CurrencyUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Currency;
import java.util.Date;
import java.util.UUID;

@Getter
public class UpdateBookingCommand {
    private final String id;
    private final String description;
    private final Integer price;
    private final Currency currency;
    private final Date subscriptionStartDate;
    private final String email;
    private final Department department;

    private UpdateBookingCommand(
            String id, String description,
            Integer price,
            Currency currency,
            Date subscriptionStartDate,
            String email,
            Department department
    ) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.subscriptionStartDate = subscriptionStartDate;
        this.email = email;
        this.department = department;
    }

    public static UpdateBookingCommand fromRequest(@NotNull String id, @NotNull UpdateBookingRequest request) {
        Currency currency = CurrencyUtil.getCurrencyOrThrow(request.currencyCode());

        return new UpdateBookingCommand(
                UUID.fromString(id).toString(),
                request.description(),
                request.price(),
                currency,
                request.subscriptionStartDate(),
                request.email(),
                request.department()
        );
    }
}
