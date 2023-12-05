package helpers;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public record MotherObject() {
    public static Clock clock = Clock.fixed(Instant.parse("2023-12-05T00:00:00Z"), ZoneId.of("Europe/Berlin"));
}
