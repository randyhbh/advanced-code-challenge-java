package com.statista.code.challenge.usecases.booking.findbydepartment;

import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.infra.inmemorydb.InMemoryBookingRepository;
import helpers.MotherObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.time.Clock;
import java.util.Collections;

class FindBookingByDepartmentUseCaseTest {

    private final Clock clock = MotherObject.clock;
    private final BookingRepository bookingRepository = new InMemoryBookingRepository(clock);

    private FindBookingByDepartmentUseCase findBookingByDepartmentUseCase;

    @BeforeEach
    void setUp() {
        findBookingByDepartmentUseCase = new FindBookingByDepartmentUseCase(NOPLogger.NOP_LOGGER, bookingRepository);
    }

    @Test
    public void checkReturnsEmptyListOfBookingsWhenNoBookingsArePresentForDepartment() {
        var department = Department.MARKETING;

        var bookingsByDepartmentResponse = findBookingByDepartmentUseCase.find(
                FindBookingByDepartmentCommand.fromRequest(department)
        );

        Assertions.assertThat(bookingsByDepartmentResponse).isNotNull();
        Assertions.assertThat(bookingsByDepartmentResponse.ids()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void checkUsedCurrencyReturnsIsContainingTheCurrenciesUsedInBookings() {
        var department = Department.MARKETING;
        var booking = MotherObject.newBooking();
        bookingRepository.save(booking);

        var bookingsByDepartmentResponse = findBookingByDepartmentUseCase.find(
                FindBookingByDepartmentCommand.fromRequest(department)
        );

        Assertions.assertThat(bookingsByDepartmentResponse).isNotNull();
        Assertions.assertThat(bookingsByDepartmentResponse.ids()).containsExactly(booking.id());
    }

}