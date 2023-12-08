package helpers;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.util.CurrencyUtil;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

public record MotherObject() {
    public static Clock clock = Clock.fixed(Instant.parse("2023-12-05T00:00:00Z"), ZoneId.of("Europe/Berlin"));

    public static Booking newMarketingBooking() {
        var currency = CurrencyUtil.getCurrencyOrThrow("EUR");
        return getBooking(1000, currency, Department.MARKETING);
    }

    public static Booking newMarketingBooking(String withCurrency) {
        var currency = CurrencyUtil.getCurrencyOrThrow(withCurrency);
        return getBooking(1000, currency, Department.MARKETING);
    }

    public static Booking newMarketingBooking(Integer withPrice) {
        var currency = CurrencyUtil.getCurrencyOrThrow("EUR");
        return getBooking(withPrice, currency, Department.MARKETING);
    }

    public static Booking newDesignBooking() {
        var currency = CurrencyUtil.getCurrencyOrThrow("EUR");
        return getBooking(1000, currency, Department.DESIGN);
    }

    public static Booking newDesignBooking(Integer withPrice) {
        var currency = CurrencyUtil.getCurrencyOrThrow("EUR");
        return getBooking(withPrice, currency, Department.DESIGN);
    }

    public static Booking newDesignBooking(String withCurrency) {
        var currency = CurrencyUtil.getCurrencyOrThrow(withCurrency);
        return getBooking(1000, currency, Department.MARKETING);
    }

    private static Booking getBooking(int price, Currency currency, Department design) {
        return new Booking(
                UUID.randomUUID().toString(),
                "A description",
                price,
                currency,
                Date.from(Instant.now(clock)),
                "example@gmail.com",
                design
        );
    }
}
