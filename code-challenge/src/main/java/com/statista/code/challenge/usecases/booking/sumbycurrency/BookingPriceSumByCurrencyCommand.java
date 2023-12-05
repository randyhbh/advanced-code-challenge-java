package com.statista.code.challenge.usecases.booking.sumbycurrency;

import com.statista.code.challenge.util.CurrencyUtil;
import lombok.Getter;

import java.util.Currency;

@Getter
public class BookingPriceSumByCurrencyCommand {
    private final Currency currency;

    private BookingPriceSumByCurrencyCommand(Currency currency) {
        this.currency = currency;
    }

    public static BookingPriceSumByCurrencyCommand fromRequest(String currency) {
        return new BookingPriceSumByCurrencyCommand(CurrencyUtil.getCurrencyOrThrow(currency));
    }
}
