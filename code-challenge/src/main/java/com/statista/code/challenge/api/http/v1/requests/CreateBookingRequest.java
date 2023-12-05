package com.statista.code.challenge.api.http.v1.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.statista.code.challenge.domain.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateBookingRequest(
        @NotNull
        @NotEmpty
        String description,
        @NotNull
        Integer price,
        @NotNull
        @NotEmpty
        @JsonProperty("currency")
        String currencyCode,
        @NotNull
        @JsonProperty("subscription_start_date")
        Date subscriptionStartDate,
        @Email
        @NotNull
        @NotEmpty
        String email,
        @NotNull
        Department department
) {
}