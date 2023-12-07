package com.statista.code.challenge.usecases.booking.findcurrency;

import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.time.Clock;
import java.util.Collections;
import java.util.Set;

class FindUsedCurrenciesUseCaseTest {

    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private FindUsedCurrenciesUseCase findUsedCurrenciesUseCase;

    @BeforeEach
    void setUp() {
        findUsedCurrenciesUseCase = new FindUsedCurrenciesUseCase(NOPLogger.NOP_LOGGER, bookingRepository);
    }

    @Test
    public void checkUsedCurrencyReturnsEmptySetWhenNoBookingsArePresent() {
        var usedCurrencies = findUsedCurrenciesUseCase.find();

        Assertions.assertThat(usedCurrencies).isNotNull();
        Assertions.assertThat(usedCurrencies.currencies()).isEqualTo(Collections.emptySet());
    }

    @Test
    public void checkUsedCurrencyReturnsIsContainingTheCurrenciesUsedInBookings() {
        var booking1 = MotherObject.newBooking();
        var booking2 = MotherObject.newBooking("USD");
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        var expectedResult = Set.of("EUR", "USD");

        var usedCurrencies = findUsedCurrenciesUseCase.find();

        Assertions.assertThat(usedCurrencies).isNotNull();
        Assertions.assertThat(usedCurrencies.currencies()).isEqualTo(expectedResult);
    }
}