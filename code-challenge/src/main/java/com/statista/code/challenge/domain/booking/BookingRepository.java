package com.statista.code.challenge.domain.booking;

import com.statista.code.challenge.domain.department.Department;

import java.util.Currency;
import java.util.List;
import java.util.Set;

public interface BookingRepository {
    Booking save(Booking booking);

    Booking find(String id);

    List<String> findByDepartment(Department department);

    Set<String> findCurrenciesUsed();

    Integer getTotalPriceByCurrency(Currency currency);

    void removeAll();
}
