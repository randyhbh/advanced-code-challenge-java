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

    public static Booking newBooking() {
        Currency currency = CurrencyUtil.getCurrencyOrThrow("EUR");
        return new Booking(
                UUID.randomUUID().toString(),
                "A description",
                CurrencyUtil.getPriceInCurrency(1000, currency),
                currency,
                Date.from(Instant.now(clock)),
                "example@gmail.com",
                Department.MARKETING
        );
    }
}
