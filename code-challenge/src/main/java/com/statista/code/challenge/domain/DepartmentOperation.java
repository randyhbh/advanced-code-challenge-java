package com.statista.code.challenge.domain;

public interface DepartmentOperation<T> {

    T doBusiness(Booking booking);

    String getBeanName();
}
