package com.statista.code.challenge.usecases.booking.dobusiness;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DoBusinessForBookingCommand {
    private final String bookingId;

    private DoBusinessForBookingCommand(String bookingId) {
        this.bookingId = bookingId;
    }

    public static DoBusinessForBookingCommand fromRequest(String bookingId) {
        return new DoBusinessForBookingCommand(UUID.fromString(bookingId).toString());
    }
}
