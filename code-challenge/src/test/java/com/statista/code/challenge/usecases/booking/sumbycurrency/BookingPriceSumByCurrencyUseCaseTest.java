package com.statista.code.challenge.usecases.booking.sumbycurrency;

import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.math.BigDecimal;
import java.time.Clock;

class BookingPriceSumByCurrencyUseCaseTest {

    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private BookingPriceSumByCurrencyUseCase bookingPriceSumByCurrencyUseCase;

    @BeforeEach
    void setUp() {
        bookingPriceSumByCurrencyUseCase = new BookingPriceSumByCurrencyUseCase(NOPLogger.NOP_LOGGER, bookingRepository);
    }

    @Test
    public void checkPriceSumByCurrencyReturnsZeroWhenNoBookingsArePresent() {
        var currencyCode = "EUR";
        var expectedResult = BigDecimal.valueOf(0, 2);
        var updateBookingCommand = BookingPriceSumByCurrencyCommand.fromRequest(currencyCode);

        var priceSumByCurrencyResponse = bookingPriceSumByCurrencyUseCase.totalPrice(updateBookingCommand);

        Assertions.assertThat(priceSumByCurrencyResponse).isNotNull();
        Assertions.assertThat(priceSumByCurrencyResponse.total()).isEqualTo(expectedResult);
        Assertions.assertThat(priceSumByCurrencyResponse.currency()).isEqualTo(currencyCode);
    }

    @Test
    public void checkPriceSumByCurrencyReturnsCorrectSumOfTheBookingsPricesPresent() {
        var booking1 = MotherObject.newBooking();
        var booking2 = MotherObject.newBooking();
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        var currencyCode = "EUR";
        var expectedResult = BigDecimal.valueOf(2000, 2);
        var updateBookingCommand = BookingPriceSumByCurrencyCommand.fromRequest(currencyCode);

        var priceSumByCurrencyResponse = bookingPriceSumByCurrencyUseCase.totalPrice(updateBookingCommand);

        Assertions.assertThat(priceSumByCurrencyResponse).isNotNull();
        Assertions.assertThat(priceSumByCurrencyResponse.total()).isEqualTo(expectedResult);
        Assertions.assertThat(priceSumByCurrencyResponse.currency()).isEqualTo(currencyCode);
    }

}