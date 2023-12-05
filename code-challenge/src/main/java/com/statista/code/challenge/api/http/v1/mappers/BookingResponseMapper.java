package com.statista.code.challenge.api.http.v1.mappers;

import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingUsedCurrenciesResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingsByDepartmentResponse;
import com.statista.code.challenge.domain.Booking;

import java.util.List;
import java.util.Set;

public class BookingResponseMapper {
    public static BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.id(),
                booking.description(),
                booking.price(),
                booking.currency(),
                booking.subscriptionDate(),
                booking.email(),
                booking.department()
        );
    }

    public static BookingsByDepartmentResponse toResponse(List<String> bookings) {
        return new BookingsByDepartmentResponse(bookings);
    }

    public static BookingUsedCurrenciesResponse toResponse(Set<String> usedCurrencies) {
        return new BookingUsedCurrenciesResponse(usedCurrencies);
    }
}
