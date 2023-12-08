package com.statista.code.challenge.api.http.v1.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.statista.code.challenge.domain.department.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UpdateBookingRequest(
        @NotNull
        @NotEmpty
        String description,
        @NotNull
        Integer price,
        @NotNull
        @NotEmpty
        @JsonProperty("currency")
        @Schema(description = "Uppercase currency code")
        String currencyCode,
        @NotNull
        @JsonProperty("subscription_start_date")
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        @Schema(type = "number")
        Instant subscriptionStartDate,
        @Email
        @NotNull
        @NotEmpty
        String email,
        @NotNull
        Department department
) {
}