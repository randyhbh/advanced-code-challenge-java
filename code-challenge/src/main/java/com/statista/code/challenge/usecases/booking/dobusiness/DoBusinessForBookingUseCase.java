package com.statista.code.challenge.usecases.booking.dobusiness;

import com.statista.code.challenge.domain.booking.BookingRepository;
import com.statista.code.challenge.infra.departments.DepartmentBeanFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class is a use case responsible for creating a new booking.
 */
@Service
public class DoBusinessForBookingUseCase {

    private final Logger logger;
    private final BookingRepository repository;
    private final DepartmentBeanFactory departmentBeanFactory;

    public DoBusinessForBookingUseCase(Logger logger, BookingRepository repository, DepartmentBeanFactory departmentBeanFactory) {
        this.logger = logger;
        this.repository = repository;
        this.departmentBeanFactory = departmentBeanFactory;
    }

    public Object doBusiness(DoBusinessForBookingCommand command) {
        var booking = repository.find(command.getBookingId());

        var department = booking.department();
        var departmentOperation = departmentBeanFactory.getDepartmentTypeBean(department);

        if (logger.isDebugEnabled()) {
            logger.info("Department Operation Bean: " + departmentOperation.getBeanName() + " retrieved");
        }

        return departmentOperation.doBusiness(booking);
    }
}
