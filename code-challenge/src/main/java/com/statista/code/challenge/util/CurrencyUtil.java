package com.statista.code.challenge.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

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

    public static String getPriceInCurrencyForLocale(Integer price, Currency currency) {
        var locale = LocaleContextHolder.getLocale();
        if (locale.getCountry().isBlank())
            locale = Locale.GERMANY;
        var numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(getPriceInCurrency(price, currency));
    }
}
