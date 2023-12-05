package com.statista.code.challenge.usecases.booking.create;

import com.statista.code.challenge.api.http.v1.requests.CreateBookingRequest;
import com.statista.code.challenge.domain.BookingRepository;
import com.statista.code.challenge.domain.Department;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

class CreateBookingUseCaseTest {
    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private CreateBookingUseCase createItemUseCase;

    @BeforeEach
    void setUp() {
        createItemUseCase = new CreateBookingUseCase(NOPLogger.NOP_LOGGER, bookingRepository);
    }

    @Test
    public void checkCreateBookingIsSuccessful() {
        String description = "A description";
        Integer price = 1000;
        String currencyCode = "EUR";
        Date subscriptionDate = Date.from(Instant.now(clock));
        String email = "example@gmail.com";
        Department department = Department.DESIGN;

        var createBookingRequest = new CreateBookingRequest(description, price, currencyCode, subscriptionDate, email, department);
        var createBookingCommand = CreateBookingCommand.fromRequest(createBookingRequest);

        var itemResponse = createItemUseCase.create(createBookingCommand);

        var itemFromDb = bookingRepository.find(itemResponse.id());

        Assertions.assertThat(itemFromDb).isNotNull();
        Assertions.assertThat(itemFromDb).usingRecursiveComparison().isEqualTo(itemResponse);
    }

}