package com.statista.code.challenge.api.http.v1.responses;

import com.statista.code.challenge.domain.Department;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record BookingResponse(
        String id,
        String description,
        BigDecimal price,
        Currency currency,
        LocalDateTime subscriptionStartDate,
        String email,
        Department department
) {
}