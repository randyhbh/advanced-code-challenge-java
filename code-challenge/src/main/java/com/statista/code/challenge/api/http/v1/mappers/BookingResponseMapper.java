package com.statista.code.challenge.api.http.v1.mappers;

import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.domain.Booking;

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
}
