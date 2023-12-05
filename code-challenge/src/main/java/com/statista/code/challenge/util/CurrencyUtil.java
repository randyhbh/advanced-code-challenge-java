package com.statista.code.challenge.util;

import java.math.BigDecimal;
import java.util.Currency;

public final class CurrencyUtil {
    public static Currency getCurrencyOrThrow(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("The provided currency code is invalid or not supported", exception.getCause());
        }
    }

    public static BigDecimal getPriceInCurrency(Integer price, Currency currency) {
        return BigDecimal.valueOf(price).movePointLeft(currency.getDefaultFractionDigits());
    }
}
