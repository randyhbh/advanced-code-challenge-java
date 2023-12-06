package com.statista.code.challenge.usecases.booking.create;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.booking.BookingRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class CreateBookingUseCase {

    private final Logger logger;
    private final BookingRepository repository;

    public CreateBookingUseCase(Logger logger, BookingRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    public Booking create(CreateBookingCommand command) {
        var booking = Booking.from(command);

        repository.save(booking);

        if (logger.isDebugEnabled()) {
            logger.info("New " + booking + " created");
        }

        //TODO: send the email after the booking creation

        return booking;
    }
}
