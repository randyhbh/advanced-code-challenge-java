package com.statista.code.challenge.usecases.booking.find;

import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.exceptions.EntityNotFoundException;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import com.statista.code.challenge.util.CurrencyUtil;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.time.Clock;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FindBookingUseCaseTest {

    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private FindBookingUseCase findBookingUseCase;

    @BeforeEach
    void setUp() {
        findBookingUseCase = new FindBookingUseCase(NOPLogger.NOP_LOGGER, bookingRepository);
    }

    @Test
    public void checkFindNotExistentBookingThrowsNotFoundException() {
        var bookingId = UUID.randomUUID().toString();
        var exception = assertThrows(
                EntityNotFoundException.class,
                () -> findBookingUseCase.find(FindBookingCommand.fromRequest(bookingId))
        );

        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("Booking with id " + bookingId + " does not exist");
    }

    @Test
    public void checkUsedCurrencyReturnsIsContainingTheCurrenciesUsedInBookings() {
        var booking = MotherObject.newBooking();
        bookingRepository.save(booking);

        var bookingResponse = findBookingUseCase.find(FindBookingCommand.fromRequest(booking.id()));

        Assertions.assertThat(bookingResponse).isNotNull();
        Assertions.assertThat(bookingResponse.id()).isEqualTo(booking.id());
        Assertions.assertThat(bookingResponse.currency()).isEqualTo(booking.currency());
        Assertions.assertThat(bookingResponse.price()).isEqualTo(
                CurrencyUtil.getPriceInCurrency(booking.price(), booking.currency())
        );
    }
}