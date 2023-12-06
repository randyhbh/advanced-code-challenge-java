package com.statista.code.challenge.api.http.v1.mappers;

import com.statista.code.challenge.api.http.v1.responses.BookingPriceSumByCurrencyResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingUsedCurrenciesResponse;
import com.statista.code.challenge.api.http.v1.responses.BookingsByDepartmentResponse;
import com.statista.code.challenge.domain.Booking;
import com.statista.code.challenge.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class BookingMapper {
    public static BookingResponse toResponse(Booking booking) {

        return new BookingResponse(
                booking.id(),
                booking.description(),
                CurrencyUtil.getPriceInCurrency(booking.price(), booking.currency()),
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

    public static BookingPriceSumByCurrencyResponse toResponse(BigDecimal totalPriceByCurrency) {
        return new BookingPriceSumByCurrencyResponse(totalPriceByCurrency);
    }
}
