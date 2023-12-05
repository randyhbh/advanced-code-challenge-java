package com.statista.code.challenge.api.http.v1.responses;

import java.util.Set;

public record BookingUsedCurrenciesResponse(Set<String> currencies) {
}