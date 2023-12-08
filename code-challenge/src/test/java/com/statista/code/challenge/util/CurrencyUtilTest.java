package com.statista.code.challenge.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

class CurrencyUtilTest {

    @Test
    void getCurrencyFormattedInTheCurrentLocale() {
        LocaleContextHolder.setLocale(Locale.GERMANY);
        var priceFormatted = CurrencyUtil.getPriceInCurrencyForLocale(30_000, CurrencyUtil.getCurrencyOrThrow("EUR"));
        Assertions.assertThat(priceFormatted).isEqualTo("300,00 €");
    }
}