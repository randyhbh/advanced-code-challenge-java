package com.statista.code.challenge.usecases.booking.dobusiness;

import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.domain.exceptions.EntityNotFoundException;
import com.statista.code.challenge.infra.departments.DepartmentBeanFactory;
import com.statista.code.challenge.infra.departments.DepartmentOperationException;
import com.statista.code.challenge.infra.departments.DesignDepartment;
import com.statista.code.challenge.infra.departments.MarketingDepartment;
import com.statista.code.challenge.infra.departments.MarketingDepartment.MarketingResponse;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DoBusinessForBookingUseCaseTest {

    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);
    private DoBusinessForBookingUseCase doBusinessForBookingUseCase;

    @BeforeEach
    void setUp() throws Exception {
        var nopLogger = NOPLogger.NOP_LOGGER;

        var departmentBeanFactory = new DepartmentBeanFactory(Map.of(
                Department.DESIGN.name(), new DesignDepartment(nopLogger),
                Department.MARKETING.name(), new MarketingDepartment(nopLogger)
        ));
        departmentBeanFactory.afterPropertiesSet();

        doBusinessForBookingUseCase = new DoBusinessForBookingUseCase(nopLogger, bookingRepository, departmentBeanFactory);
    }

    @Test
    public void checkDoBusinessForNonExistentBookingsThrowsNotFoundException() {
        var bookingId = UUID.randomUUID().toString();

        var exception = assertThrows(
                EntityNotFoundException.class,
                () -> doBusinessForBookingUseCase.doBusiness(DoBusinessForBookingCommand.fromRequest(bookingId))
        );

        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("Booking with id " + bookingId + " does not exist");
    }

    @Test
    public void checkDoBusinessForExistentMarketingBookingReturnsThatRequiresMoreMoneyResult() {
        var booking = MotherObject.newMarketingBooking();
        bookingRepository.save(booking);

        var response = doBusinessForBookingUseCase.doBusiness(DoBusinessForBookingCommand.fromRequest(booking.id()));

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isExactlyInstanceOf(MarketingResponse.class);

        var marketingResponse = (MarketingResponse) response;
        Assertions.assertThat(marketingResponse.result()).isFalse();
        Assertions.assertThat(marketingResponse.extraAmount()).isEqualTo(BigDecimal.valueOf(1000, 2));
        Assertions.assertThat(marketingResponse.currency()).isEqualTo("EUR");
        Assertions.assertThat(marketingResponse.description()).isEqualTo("We require an extra payment of the provided amount because of extra work");
    }

    @Test
    public void checkDoBusinessForExistentMarketingBookingReturnsThatTheWorkWillBeStartResult() {
        var booking = MotherObject.newMarketingBooking(3000);
        bookingRepository.save(booking);

        var response = doBusinessForBookingUseCase.doBusiness(DoBusinessForBookingCommand.fromRequest(booking.id()));

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isExactlyInstanceOf(MarketingResponse.class);

        var marketingResponse = (MarketingResponse) response;
        Assertions.assertThat(marketingResponse.result()).isTrue();
        Assertions.assertThat(marketingResponse.extraAmount()).isEqualTo(BigDecimal.valueOf(0, 2));
        Assertions.assertThat(marketingResponse.currency()).isEqualTo("EUR");
        Assertions.assertThat(marketingResponse.description()).isEqualTo("Everything ready to start the work");
    }

    @Test
    public void checkDoBusinessForExistentDesignBookingThrowsException() {
        var booking = MotherObject.newDesignBooking();
        bookingRepository.save(booking);

        var exception = assertThrows(
                DepartmentOperationException.class,
                () -> doBusinessForBookingUseCase.doBusiness(DoBusinessForBookingCommand.fromRequest(booking.id()))
        );

        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getProperties()).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("Problems while doing business with department: DESIGN");
    }

    @Test
    public void checkDoBusinessForExistentDesignBookingReturnsTheExpectedStringConfirmation() {
        var booking = MotherObject.newDesignBooking(20_000);
        bookingRepository.save(booking);

        var response = doBusinessForBookingUseCase.doBusiness(DoBusinessForBookingCommand.fromRequest(booking.id()));

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isExactlyInstanceOf(String.class);
        Assertions.assertThat(response).isEqualTo("Your Design will be send to you ASAP ;)");
    }
}