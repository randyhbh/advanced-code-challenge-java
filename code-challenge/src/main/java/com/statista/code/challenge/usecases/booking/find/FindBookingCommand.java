package com.statista.code.challenge.usecases.booking.find;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FindBookingCommand {
    private final String id;

    private FindBookingCommand(String id) {
        this.id = id;
    }

    public static FindBookingCommand fromRequest(@NotNull String id) {
        return new FindBookingCommand(UUID.fromString(id).toString());
    }
}
