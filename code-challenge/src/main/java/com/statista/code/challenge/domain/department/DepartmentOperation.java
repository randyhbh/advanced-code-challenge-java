package com.statista.code.challenge.domain.department;

import com.statista.code.challenge.domain.booking.Booking;

public interface DepartmentOperation<T> {

    T doBusiness(Booking booking);

    String getBeanName();
}
