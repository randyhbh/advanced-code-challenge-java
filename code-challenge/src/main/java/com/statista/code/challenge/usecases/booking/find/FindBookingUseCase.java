package com.statista.code.challenge.usecases.booking.find;

import com.statista.code.challenge.api.http.v1.mappers.BookingResponseMapper;
import com.statista.code.challenge.api.http.v1.responses.BookingResponse;
import com.statista.code.challenge.domain.Booking;
import com.statista.code.challenge.domain.BookingRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class FindBookingUseCase {

    private final Logger logger;
    private final BookingRepository repository;

    public FindBookingUseCase(Logger logger, BookingRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    public BookingResponse find(FindBookingCommand command) {
        Booking booking = repository.find(command.getId());

        if (logger.isDebugEnabled()) {
            logger.info("Retrieved " + booking);
        }

        return BookingResponseMapper.toResponse(booking);
    }
}
