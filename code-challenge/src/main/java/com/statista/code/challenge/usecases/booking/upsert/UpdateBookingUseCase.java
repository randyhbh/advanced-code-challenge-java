package com.statista.code.challenge.usecases.booking.upsert;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.booking.BookingRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class UpdateBookingUseCase {

    private final Logger logger;
    private final BookingRepository repository;

    public UpdateBookingUseCase(Logger logger, BookingRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    public Booking upsert(UpdateBookingCommand command) {
        var booking = Booking.from(command);

        repository.save(booking);

        if (logger.isDebugEnabled()) {
            logger.info("Upserted " + booking);
        }

        return booking;
    }
}
