package com.statista.code.challenge.usecases.booking.findcurrency;

import com.statista.code.challenge.api.http.v1.mappers.BookingResponseMapper;
import com.statista.code.challenge.api.http.v1.responses.BookingUsedCurrenciesResponse;
import com.statista.code.challenge.domain.BookingRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class FindUsedCurrenciesUseCase {

    private final Logger logger;
    private final BookingRepository repository;

    public FindUsedCurrenciesUseCase(Logger logger, BookingRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    public BookingUsedCurrenciesResponse find() {
        Set<String> currenciesUsed = repository.findCurrenciesUsed();

        if (logger.isDebugEnabled()) {
            logger.info("Currencies used " + currenciesUsed);
        }

        return BookingResponseMapper.toResponse(currenciesUsed);
    }
}