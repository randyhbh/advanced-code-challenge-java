package com.statista.code.challenge.usecases.booking.create;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import com.statista.code.challenge.infra.mail.MailService;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.time.Clock;
import java.time.Instant;

class CreateBookingUseCaseTest {
    private final Clock clock = MotherObject.clock;
    private final MailService mailService = new MailService(NOPLogger.NOP_LOGGER);
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private CreateBookingUseCase createItemUseCase;

    @BeforeEach
    void setUp() {
        createItemUseCase = new CreateBookingUseCase(NOPLogger.NOP_LOGGER, bookingRepository, mailService);
    }

    @Test
    public void checkCreateBookingIsSuccessful() {
        var description = "A description";
        Integer price = 1000;
        var currencyCode = "EUR";
        var subscriptionDate = Instant.now(clock);
        var email = "example@gmail.com";
        var department = Department.DESIGN;

        var createBookingRequest = new CreateBookingRequest(description, price, currencyCode, subscriptionDate, email, department);
        var createBookingCommand = CreateBookingCommand.fromRequest(createBookingRequest);

        var booking = createItemUseCase.create(createBookingCommand);

        var bookingFromDb = bookingRepository.find(booking.id());

        Assertions.assertThat(bookingFromDb).isNotNull();
        Assertions.assertThat(bookingFromDb).usingRecursiveComparison().isEqualTo(booking);
    }

}