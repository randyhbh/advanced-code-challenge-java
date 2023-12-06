package com.statista.code.challenge.usecases.booking.sumbycurrency;

import com.statista.code.challenge.api.http.v1.mappers.BookingMapper;
import com.statista.code.challenge.api.http.v1.responses.BookingPriceSumByCurrencyResponse;
import com.statista.code.challenge.domain.booking.BookingRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class BookingPriceSumByCurrencyUseCase {

    private final Logger logger;
    private final BookingRepository repository;

    public BookingPriceSumByCurrencyUseCase(Logger logger, BookingRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    public BookingPriceSumByCurrencyResponse totalPrice(BookingPriceSumByCurrencyCommand command) {
        BigDecimal totalPriceByCurrency = repository.getTotalPriceByCurrency(command.getCurrency());

        if (logger.isDebugEnabled()) {
            logger.info("Total price " + totalPriceByCurrency + " for currency " + command.getCurrency().getCurrencyCode());
        }

        return BookingMapper.toResponse(totalPriceByCurrency);
    }
}
