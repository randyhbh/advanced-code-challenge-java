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

    private final Logger logger;

    public MarketingDepartment(Logger logger) {
        this.logger = logger;
    }

    @Override
    public MarketingResponse doBusiness(Booking booking) {
        logger.info("Performing Marketing Operation on -> " + booking);

        return new MarketingResponse(
                true,
                CurrencyUtil.getPriceInCurrency(5000, booking.currency()),
                "Extra work"
        );
    }

    @Override
    public String getBeanName() {
        return Department.MARKETING.name();
    }

    public record MarketingResponse(Boolean result, BigDecimal extraAmount, String description) {
    }
}
