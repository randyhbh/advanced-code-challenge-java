package com.statista.code.challenge.usecases.booking.upsert;

import com.statista.code.challenge.api.http.v1.requests.UpdateBookingRequest;
import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

class UpdateBookingUseCaseTest {
    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private UpdateBookingUseCase updateBookingUseCase;

    @BeforeEach
    void setUp() {
        updateBookingUseCase = new UpdateBookingUseCase(NOPLogger.NOP_LOGGER, bookingRepository);
    }

    @Test
    public void checkUpdateANonExistingBookingCreateANewOneSuccessfully() {
        var bookingId = UUID.randomUUID().toString();
        var description = "A description";
        Integer price = 1000;
        var currencyCode = "EUR";
        var subscriptionDate =Instant.now(clock);
        var email = "example@gmail.com";
        var department = Department.DESIGN;

        var updateBookingRequest = new UpdateBookingRequest(description, price, currencyCode, subscriptionDate, email, department);
        var updateBookingCommand = UpdateBookingCommand.fromRequest(bookingId, updateBookingRequest);

        var booking = updateBookingUseCase.upsert(updateBookingCommand);

        var bookingFromDb = bookingRepository.find(booking.id());

        Assertions.assertThat(bookingFromDb).isNotNull();
        Assertions.assertThat(bookingFromDb).usingRecursiveComparison().isEqualTo(booking);
    }

    @Test
    public void checkUpdateExistingBookingSuccessfully() {
        Booking existingBooking = MotherObject.newMarketingBooking();
        bookingRepository.save(existingBooking);

        var bookingId = existingBooking.id();
        var description = "A description";
        Integer price = 2000;
        var currencyCode = "EUR";
        var subscriptionDate = Instant.now(clock);
        var email = "example@gmail.com";
        var department = Department.DESIGN;

        var updateBookingRequest = new UpdateBookingRequest(description, price, currencyCode, subscriptionDate, email, department);
        var updateBookingCommand = UpdateBookingCommand.fromRequest(bookingId, updateBookingRequest);

        var updatedBooking = updateBookingUseCase.upsert(updateBookingCommand);

        Assertions.assertThat(updatedBooking).isNotNull();
        Assertions.assertThat(existingBooking).usingRecursiveComparison().isNotEqualTo(updatedBooking);
        Assertions.assertThat(existingBooking.price()).isEqualTo(1000);
        Assertions.assertThat(updatedBooking.price()).isEqualTo(2000);
        Assertions.assertThat(existingBooking.price()).isLessThan(updatedBooking.price());
    }
}