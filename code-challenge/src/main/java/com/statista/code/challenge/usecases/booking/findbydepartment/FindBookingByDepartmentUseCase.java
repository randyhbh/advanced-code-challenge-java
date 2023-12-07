package com.statista.code.challenge.usecases.booking.findbydepartment;

import com.statista.code.challenge.api.http.v1.mappers.BookingMapper;
import com.statista.code.challenge.api.http.v1.responses.BookingsByDepartmentResponse;
import com.statista.code.challenge.domain.booking.BookingRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class FindBookingByDepartmentUseCase {

    private final Logger logger;
    private final BookingRepository repository;

    public FindBookingByDepartmentUseCase(Logger logger, BookingRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    public BookingsByDepartmentResponse find(FindBookingByDepartmentCommand command) {
        var bookings = repository.findByDepartment(command.getDepartment());

        if (logger.isDebugEnabled()) {
            logger.info("Retrieved " + bookings);
        }

        return BookingMapper.toResponse(bookings);
    }
}
