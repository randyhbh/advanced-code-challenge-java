package com.statista.code.challenge.infra.departments;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.domain.department.DepartmentOperation;
import com.statista.code.challenge.util.CurrencyUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MarketingDepartment implements DepartmentOperation<MarketingDepartment.MarketingResponse> {

    private static final int MINIMUM_AMOUNT_IN_CENTS = 2000;
    private final Logger logger;

    public MarketingDepartment(Logger logger) {
        this.logger = logger;
    }

    @Override
    public MarketingResponse doBusiness(Booking booking) {
        logger.info("Performing Marketing Operation on -> " + booking);

        var price = booking.price();
        var currency = booking.currency();

        var finalResult = price > MINIMUM_AMOUNT_IN_CENTS;

        var extraAmount = BigDecimal.valueOf(0, 2);
        var description = "Everything ready to start the work";

        if (!finalResult) {
            var differenceInCents = MINIMUM_AMOUNT_IN_CENTS - price;
            extraAmount = CurrencyUtil.getPriceInCurrency(differenceInCents, currency);
            description = "We require an extra payment of the provided amount because of extra work";
        }

        return new MarketingResponse(finalResult, extraAmount, currency.getCurrencyCode(), description);
    }

    @Override
    public String getBeanName() {
        return Department.MARKETING.name();
    }

    public record MarketingResponse(Boolean result, BigDecimal extraAmount, String currency, String description) {
    }
}
