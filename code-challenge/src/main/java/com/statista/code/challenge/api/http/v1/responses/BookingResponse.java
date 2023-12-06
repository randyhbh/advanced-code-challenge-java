package com.statista.code.challenge.api.http.v1.responses;

import com.statista.code.challenge.domain.department.Department;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public record BookingResponse(
        String id,
        String description,
        BigDecimal price,
        Currency currency,
        Date subscriptionStartDate,
        String email,
        Department department
) {
}