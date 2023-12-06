package com.statista.code.challenge.usecases.booking.create;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.domain.Department;
import com.statista.code.challenge.util.CurrencyUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Currency;
import java.util.Date;

@Getter
public class CreateBookingCommand {
    private final String description;
    private final Integer price;
    private final Currency currency;
    private final Date subscriptionStartDate;
    private final String email;
    private final Department department;

    private CreateBookingCommand(
            String description,
            Integer price,
            Currency currency,
            Date subscriptionStartDate,
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
        Currency currency = CurrencyUtil.getCurrencyOrThrow(request.currencyCode());

        return new CreateBookingCommand(
                request.description(),
                request.price(),
                currency,
                request.subscriptionStartDate(),
                request.email(),
                request.department()
        );
    }
}
