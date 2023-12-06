package com.statista.code.challenge.api.http.v1;

import com.statista.code.challenge.api.http.v1.responses.BookingPriceSumByCurrencyResponse;
import com.statista.code.challenge.usecases.booking.sumbycurrency.BookingPriceSumByCurrencyCommand;
import com.statista.code.challenge.usecases.booking.sumbycurrency.BookingPriceSumByCurrencyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookingservice")
public class BookingOperationsController {
    private final BookingPriceSumByCurrencyUseCase priceSumByCurrencyUseCase;

    public BookingOperationsController(BookingPriceSumByCurrencyUseCase priceSumByCurrencyUseCase) {
        this.priceSumByCurrencyUseCase = priceSumByCurrencyUseCase;
    }

    @Operation(summary = "Return the price sum of all bookings with the same currency")
    @GetMapping("/sum/{currency}")
    @ResponseStatus(HttpStatus.OK)
    public BookingPriceSumByCurrencyResponse getSumByCurrency(@PathVariable String currency) {
        return priceSumByCurrencyUseCase.totalPrice(BookingPriceSumByCurrencyCommand.fromRequest(currency));
    }

}