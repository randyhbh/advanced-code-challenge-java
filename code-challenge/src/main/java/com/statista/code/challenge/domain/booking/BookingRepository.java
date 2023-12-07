package com.statista.code.challenge.domain.booking;

import com.statista.code.challenge.domain.department.Department;

import java.util.Currency;
import java.util.List;
import java.util.Set;

public interface BookingRepository {
    public Booking save(Booking booking);

    public Booking find(String id);

    public List<Booking> findAll();

    public List<String> findByDepartment(Department department);

    public Set<String> findCurrenciesUsed();

    public Integer getTotalPriceByCurrency(Currency currency);
}
