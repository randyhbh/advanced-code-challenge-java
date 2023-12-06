package com.statista.code.challenge.usecases.booking.create;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.infra.mail.MailMessageRender;
import com.statista.code.challenge.infra.mail.MailService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class CreateBookingUseCase {

    private final Logger logger;
    private final BookingRepository repository;
    private final MailService mailService;

    public CreateBookingUseCase(Logger logger, BookingRepository repository, MailService mailService) {
        this.logger = logger;
        this.repository = repository;
        this.mailService = mailService;
    }

    public Booking create(CreateBookingCommand command) {
        var booking = Booking.from(command);

        repository.save(booking);

        if (logger.isDebugEnabled()) {
            logger.info("New " + booking + " created");
        }

        mailService.send(MailMessageRender.fromBooking(booking));

        return booking;
    }
}
