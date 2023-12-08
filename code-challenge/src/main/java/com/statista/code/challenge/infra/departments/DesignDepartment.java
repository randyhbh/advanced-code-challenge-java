package com.statista.code.challenge.infra.departments;

import com.statista.code.challenge.domain.booking.Booking;
import com.statista.code.challenge.domain.department.Department;
import com.statista.code.challenge.domain.department.DepartmentOperation;
import com.statista.code.challenge.util.CurrencyUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DesignDepartment implements DepartmentOperation<String> {

    private static final Integer MINIMUM_VALUE_IN_CENTS = 20_000;

    private final Logger logger;

    public DesignDepartment(Logger logger) {
        this.logger = logger;
    }

    @Override
    public String doBusiness(Booking booking) {
        if (booking.price() < MINIMUM_VALUE_IN_CENTS) {
            var priceInCurrencyForLocale = CurrencyUtil.getPriceInCurrencyForLocale(MINIMUM_VALUE_IN_CENTS, booking.currency());
            throw new DepartmentOperationException(
                    getBeanName(),
                    Collections.singletonMap(
                            "price",
                            "Minimum price must be higher than " +
                                    priceInCurrencyForLocale
                    )
            );
        }

        logger.info("Performing Design Operation on -> " + booking);

        return "Your Design will be send to you ASAP ;)";
    }

    @Override
    public String getBeanName() {
        return Department.DESIGN.name();
    }
}
